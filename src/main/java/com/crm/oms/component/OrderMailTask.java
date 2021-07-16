package com.crm.oms.component;

import com.crm.oms.common.utils.ShowMail;
import com.crm.oms.enums.MailTypeEnum;
import com.crm.oms.model.MailManagement;
import com.crm.oms.model.MailOrder;
import com.crm.oms.service.MailManagementService;
import com.crm.oms.service.MailOrderService;
import com.crm.oms.service.RedisService;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.crm.oms.common.utils.ShowMail.*;

/**
 * @author fangyang
 * @create 2021-04-27
 * @since 1.0.0
 */

@Component
public class OrderMailTask {

    private Logger LOGGER = LoggerFactory.getLogger(OrderMailTask.class);

    String format = "yyyy-MM-dd HH:mm:ss";

    @Resource
    private RedisService redisService;

    @Resource
    private MailManagementService mailManagementService;

    @Resource
    private MailOrderService mailOrderService;


    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每2分钟扫描一次，扫描设定超时时间之前下的订单，如果没支付则取消该订单
     */
    @Scheduled(cron = "0 0/10 * ? * ?")
    private void cancelTimeOutOrder() throws ParseException {

        LOGGER.info("开始解析邮件");

        // 从redis取出上次执行的时间
        String stringDate = redisService.get("order:mail:task");

        Date date = new Date();
        if (StringUtil.isNotEmpty(stringDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(stringDate);
        } else {
            Calendar cal = Calendar.getInstance();
            //获取前面的时间用-负号
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, -15);
            date = cal.getTime();
        }

        List<MailManagement> mailManagementList = mailManagementService.list();
        for (MailManagement mailManagement : mailManagementList) {

            Integer serviceType = mailManagement.getServiceType();
            MailTypeEnum mailTypeEnum = MailTypeEnum.ofType(serviceType);
            String host = mailTypeEnum.getMessage();


            // 163登录信息
            String username = mailManagement.getEmail();
            String password = mailManagement.getAuthorizationCode();
            String protocol = mailTypeEnum.getNode();
            String startDate = date + "";
            try {
                List<Message> messageList = filterMessage(getWEMessage(host, username, password, protocol), null, startDate);
                for (Message message : messageList) {
                    ShowMail showMail = new ShowMail((MimeMessage) message);
                    showMail.getMailContent(message);

                    // 开始封装邮件的订单
                    MailOrder mailOrder = new MailOrder();
                    mailOrder.build0(showMail);
                    mailOrderService.save(mailOrder);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
