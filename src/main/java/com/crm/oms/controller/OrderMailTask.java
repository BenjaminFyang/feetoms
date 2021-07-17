package com.crm.oms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crm.oms.common.utils.ShowMail;
import com.crm.oms.enums.MailOrderRecordEnum;
import com.crm.oms.enums.MailTypeEnum;
import com.crm.oms.mapper.MailManagementMapper;
import com.crm.oms.mapper.MailOrderUkNumberMapper;
import com.crm.oms.model.MailManagement;
import com.crm.oms.model.MailOrder;
import com.crm.oms.model.MailOrderRecord;
import com.crm.oms.model.MailOrderUkNumber;
import com.crm.oms.service.*;
import com.github.pagehelper.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

@Slf4j
@Api(tags = "邮件同步")
@RestController
@RequestMapping("/mailManagement")
public class OrderMailTask {

    @Resource
    private MailManagementMapper mailManagementMapper;

    @Resource
    private MailOrderService mailOrderService;

    @Resource
    private MailOrderUkNumberMapper mailOrderUkNumberMapper;

    @Resource
    private MailOrderRecordService mailOrderRecordService;

    String format = "yyyy-MM-dd HH:mm:ss";

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每2分钟扫描一次，扫描设定超时时间之前下的订单，如果没支付则取消该订单
     */
    @ApiOperation(value = "1、邮件")
//    @Scheduled(cron = "0 0/10 * ? * ?")
    @RequestMapping(value = "/list22", method = RequestMethod.POST)
    private void cancelTimeOutOrder() {

        log.info("开始解析邮件");

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        //获取前面的时间用-负号
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -60);
        date = cal.getTime();
        LambdaQueryWrapper<MailManagement> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<MailManagement> mailManagementList = mailManagementMapper.selectList(objectLambdaQueryWrapper.eq(MailManagement::getPassNot, 0));
        for (MailManagement mailManagement : mailManagementList) {

            Integer serviceType = mailManagement.getServiceType();
            MailTypeEnum mailTypeEnum = MailTypeEnum.ofType(serviceType);
            String host = mailTypeEnum.getMessage();

            // 163登录信息
            String username = mailManagement.getEmail();
            String password = mailManagement.getAuthorizationCode();
            String protocol = mailTypeEnum.getNode();

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String startDate = sdf.format(date);


            try {
                List<Message> messageList = filterMessage(getWEMessage(host, username, password, protocol), null, startDate);
                for (Message message : messageList) {
                    ShowMail showMail = new ShowMail((MimeMessage) message);
                    showMail.getMailContent(message);

                    // 得到当前邮件的唯一k
                    String messageID = ((MimeMessage) message).getMessageID();
                    String uk = messageID.substring(messageID.indexOf("<") + 1, messageID.indexOf(".xt.local"));

                    LambdaQueryWrapper<MailOrderUkNumber> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    LambdaQueryWrapper<MailOrderUkNumber> numberLambdaQueryWrapper = lambdaQueryWrapper.eq(MailOrderUkNumber::getUkNumber, uk);
                    List<MailOrderUkNumber> mailOrderUkNumberList = mailOrderUkNumberMapper.selectList(numberLambdaQueryWrapper);
                    if (!CollectionUtils.isEmpty(mailOrderUkNumberList)) {
                        log.error("邮件已经解析过了请核实uk={}", uk);
                        continue;
                    }

                    // 开始封装邮件的订单
                    MailOrder mailOrder = new MailOrder();
                    mailOrder.build0(showMail);
                    mailOrderService.save(mailOrder);

                    // 增加操作日志
                    mailOrderRecordService.insert(mailOrder.getId(), MailOrderRecordEnum.TYPE0);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
