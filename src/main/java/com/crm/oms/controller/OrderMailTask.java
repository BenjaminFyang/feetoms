package com.crm.oms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.crm.oms.common.utils.ShowMail;
import com.crm.oms.enums.MailOrderRecordEnum;
import com.crm.oms.enums.MailTypeEnum;
import com.crm.oms.enums.OrderStatusEnum;
import com.crm.oms.mapper.MailManagementMapper;
import com.crm.oms.mapper.MailOrderUkNumberMapper;
import com.crm.oms.model.MailManagement;
import com.crm.oms.model.MailOrder;
import com.crm.oms.model.MailOrderUkNumber;
import com.crm.oms.service.MailOrderRecordService;
import com.crm.oms.service.MailOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.crm.oms.common.utils.ShowMail.filterMessage;
import static com.crm.oms.common.utils.ShowMail.getWEMessage;

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
    private List<String> cancelTimeOutOrder() {

        log.info("开始解析邮件");

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        //获取前面的时间用-负号
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -60);
        date = cal.getTime();
        LambdaQueryWrapper<MailManagement> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<MailManagement> mailManagementList = mailManagementMapper.selectList(objectLambdaQueryWrapper.eq(MailManagement::getPassNot, 0));

        List<String> objects = new ArrayList<>();
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

                    // 发送主题
                    String subject = showMail.getSubject();

                    // 发送时间
                    String sentDate = showMail.getSentDate();

                    // 发送人地址
                    String from = showMail.getFrom();

                    // 收件人
                    String mailAddress = showMail.getMailAddress("to");


                    showMail.getMailContent(message);
                    // 得到当前邮件的唯一k
                    String uk = ((MimeMessage) message).getMessageID();
//                    String uk = messageID.substring(messageID.indexOf("<") + 1, messageID.indexOf(".xt.local"));


                    LambdaQueryWrapper<MailOrderUkNumber> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    LambdaQueryWrapper<MailOrderUkNumber> numberLambdaQueryWrapper = lambdaQueryWrapper.eq(MailOrderUkNumber::getUkNumber, uk);
                    List<MailOrderUkNumber> mailOrderUkNumberList = mailOrderUkNumberMapper.selectList(numberLambdaQueryWrapper);
                    if (!CollectionUtils.isEmpty(mailOrderUkNumberList)) {
                        log.error("邮件已经解析过了");
                        continue;
                    } else {
                        log.error("开始解析邮件插入订单uk={}", uk);
                        // 首次下单 已下单
                        objects.add(subject);
                        if (subject.contains("Thank You for Your Order")) {
                            try {
                                // 开始封装邮件的订单
                                MailOrder mailOrder = new MailOrder();
                                mailOrder.build0(showMail, OrderStatusEnum.TYPE0);
                                mailOrderService.save(mailOrder);

                                // 增加操作日志
                                mailOrderRecordService.insert(mailOrder.getId(), MailOrderRecordEnum.TYPE0);

                                // 增加邮件订单标识信息
                                buildEmail(uk, mailOrder);
                            } catch (Exception exception) {
                                log.error("OrderMailTask邮件解析已下单异常请核实subject={},sentDate={},mailAddress={}", subject, sentDate, mailAddress, exception);


                                // todo 将数据插入表中
                            }

                            continue;
                        }
                    }

                    // 已发货
                    if (subject.contains("Looking for Your Nike Order")) {

                        String orderNumber = MailOrder.getOrderNumber(showMail);
                        // 查询订单是否存在
                        LambdaQueryWrapper<MailOrder> objectLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
                        objectLambdaQueryWrapper1.eq(MailOrder::getOrderNumber, orderNumber);
                        List<MailOrder> mailOrderList = mailOrderService.list(objectLambdaQueryWrapper1);
                        if (CollectionUtils.isEmpty(mailOrderList)) {
                            log.error("OrderMailTask邮件已下单状态不存在uk={}", uk);
                            continue;
                        }

                        MailOrder mailOrder = new MailOrder();
                        try {
                            mailOrder.build2(showMail, mailOrderList.get(0).getId(), OrderStatusEnum.TYPE2);

                            mailOrderService.updateById(mailOrder);

                            // 增加操作日志
                            mailOrderRecordService.insert(mailOrder.getId(), MailOrderRecordEnum.TYPE2);

                            // 增加邮件订单标识信息
                            buildEmail(uk, mailOrder);

                        } catch (Exception exception) {
                            log.error("OrderMailTask邮件解析已发货异常请核实subject={},sentDate={},mailAddress={}", subject, sentDate, mailAddress, exception);
                            mailOrder.setOrderState(OrderStatusEnum.TYPE7.getCode());
                            mailOrderService.updateById(mailOrder);
                            continue;
                        }


                    }

                    // 派件中
                    if (subject.contains("Your Shipment is Arriving Soon")) {

                        String orderNumber = MailOrder.getOrderNumber(showMail);
                        // 查询订单是否存在
                        LambdaQueryWrapper<MailOrder> objectLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
                        objectLambdaQueryWrapper1.eq(MailOrder::getOrderNumber, orderNumber);
                        MailOrder orderServiceOne = mailOrderService.getOne(objectLambdaQueryWrapper1);
                        if (orderServiceOne == null) {
                            log.error("OrderMailTask邮件下单状态不存在uk={}", uk);
                            continue;
                        }

                        MailOrder mailOrder = new MailOrder();
                        try {
                            mailOrder.build3(orderServiceOne.getId(), OrderStatusEnum.TYPE3);
                            mailOrderService.updateById(mailOrder);

                            // 增加操作日志
                            mailOrderRecordService.insert(mailOrder.getId(), MailOrderRecordEnum.TYPE3);

                            // 增加邮件订单标识信息
                            buildEmail(uk, mailOrder);
                        } catch (Exception exception) {
                            log.error("OrderMailTask邮件解析派件中异常请核实subject={},sentDate={},mailAddress={}", subject, sentDate, mailAddress, exception);
                            mailOrder.setOrderState(OrderStatusEnum.TYPE7.getCode());
                            mailOrderService.updateById(mailOrder);
                            continue;
                        }
                    }

                    // 派件延迟
                    if (subject.contains("Your Shipment is Delayed")) {

                        String orderNumber = MailOrder.getOrderNumber(showMail);
                        LambdaQueryWrapper<MailOrder> objectLambdaQueryWrapper4 = new LambdaQueryWrapper<>();
                        objectLambdaQueryWrapper4.eq(MailOrder::getOrderNumber, orderNumber);
                        MailOrder orderServiceOne = mailOrderService.getOne(objectLambdaQueryWrapper4);
                        if (orderServiceOne == null) {
                            log.error("OrderMailTask邮件派单延迟不存在uk={}", uk);
                            continue;
                        }

                        MailOrder mailOrder = new MailOrder();
                        try {
                            mailOrder.build3(orderServiceOne.getId(), OrderStatusEnum.TYPE4);
                            mailOrderService.updateById(mailOrder);

                            // 增加操作日志
                            mailOrderRecordService.insert(mailOrder.getId(), MailOrderRecordEnum.TYPE4);

                            // 增加邮件订单标识信息
                            buildEmail(uk, mailOrder);
                        } catch (Exception exception) {
                            log.error("OrderMailTask邮件解析派件延迟异常请核实subject={},sentDate={},mailAddress={}", subject, sentDate, mailAddress, exception);
                            mailOrder.setOrderState(OrderStatusEnum.TYPE7.getCode());
                            mailOrderService.updateById(mailOrder);
                            continue;
                        }
                    }


                    // 已签收
                    if (subject.contains("Your Order Has Arrived")) {

                        String orderNumber = MailOrder.getOrderNumber(showMail);
                        LambdaQueryWrapper<MailOrder> objectLambdaQueryWrapper4 = new LambdaQueryWrapper<>();
                        objectLambdaQueryWrapper4.eq(MailOrder::getOrderNumber, orderNumber);
                        List<MailOrder> mailOrderList = mailOrderService.list(objectLambdaQueryWrapper4);
                        if (CollectionUtils.isEmpty(mailOrderList)) {
                            log.error("OrderMailTask邮件派件延迟不存在uk={}", uk);
                            continue;
                        }

                        MailOrder mailOrder = new MailOrder();
                        try {
                            mailOrder.build3(mailOrderList.get(0).getId(), OrderStatusEnum.TYPE5);
                            mailOrderService.updateById(mailOrder);

                            // 增加操作日志
                            mailOrderRecordService.insert(mailOrder.getId(), MailOrderRecordEnum.TYPE5);

                            // 增加邮件订单标识信息
                            buildEmail(uk, mailOrder);
                        } catch (Exception exception) {
                            log.error("OrderMailTask邮件解析已签收异常请核实subject={},sentDate={},mailAddress={}", subject, sentDate, mailAddress, exception);
                            mailOrder.setOrderState(OrderStatusEnum.TYPE7.getCode());
                            mailOrderService.updateById(mailOrder);
                            continue;
                        }
                    }

                    // 召回
                    if (subject.contains("Your Shipment Couldn’t Be Delivered")) {

                        String orderNumber = MailOrder.getOrderNumber(showMail);
                        LambdaQueryWrapper<MailOrder> objectLambdaQueryWrapper4 = new LambdaQueryWrapper<>();
                        objectLambdaQueryWrapper4.eq(MailOrder::getOrderNumber, orderNumber);
                        MailOrder orderServiceOne = mailOrderService.getOne(objectLambdaQueryWrapper4);
                        if (orderServiceOne == null) {
                            log.error("OrderMailTask邮件派件已签收不存在uk={}", uk);
                            continue;
                        }

                        MailOrder mailOrder = new MailOrder();
                        try {
                            mailOrder.build3(orderServiceOne.getId(), OrderStatusEnum.TYPE6);
                            mailOrderService.updateById(mailOrder);

                            // 增加操作日志
                            mailOrderRecordService.insert(mailOrder.getId(), MailOrderRecordEnum.TYPE6);

                            // 增加邮件订单标识信息
                            buildEmail(uk, mailOrder);
                        } catch (Exception exception) {
                            log.error("OrderMailTask邮件解析召回异常请核实subject={},sentDate={},mailAddress={}", subject, sentDate, mailAddress, exception);
                            mailOrder.setOrderState(OrderStatusEnum.TYPE7.getCode());
                            mailOrderService.updateById(mailOrder);
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return objects;

    }

    private void buildEmail(String uk, MailOrder mailOrder) {
        MailOrderUkNumber mailOrderUkNumber = new MailOrderUkNumber();
        mailOrderUkNumber.setMailOrderId(mailOrder.getId());
        mailOrderUkNumber.setUkNumber(uk);
        mailOrderUkNumber.setUpdateTime(new Date());
        mailOrderUkNumber.setCreateTime(new Date());
        mailOrderUkNumberMapper.insert(mailOrderUkNumber);
    }

}
