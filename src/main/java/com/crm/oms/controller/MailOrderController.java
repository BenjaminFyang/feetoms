package com.crm.oms.controller;


import com.crm.oms.common.api.CommonPage;
import com.crm.oms.common.api.CommonResult;
import com.crm.oms.dto.MailOrderParam;
import com.crm.oms.enums.OrderStatusEnum;
import com.crm.oms.model.MailOrder;
import com.crm.oms.service.MailOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 邮件订单信息表 前端控制器
 *
 * @author fangyang
 * @since 2021-07-12
 */

@Api(tags = "订单信息管理")
@RestController
@RequestMapping("/mailOrder")
public class MailOrderController {

    private Logger LOGGER = LoggerFactory.getLogger(MailOrderController.class);


    @Resource
    private MailOrderService mailOrderService;


    @ApiOperation(value = "订单信息列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult<CommonPage<MailOrder>> login(@RequestBody MailOrderParam mailOrderParam, BindingResult result) {


        OrderStatusEnum type0 = OrderStatusEnum.TYPE0;


        return null;

    }

}

