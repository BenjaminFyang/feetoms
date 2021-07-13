package com.crm.oms.controller;


import com.crm.oms.service.MailOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 邮件订单信息表 前端控制器
 *
 * @author fangyang
 * @since 2021-07-12
 */
@RestController
@RequestMapping("/mailOrder")
public class MailOrderController {

    private Logger LOGGER = LoggerFactory.getLogger(MailOrderController.class);


    @Resource
    private MailOrderService mailOrderService;


    //

}

