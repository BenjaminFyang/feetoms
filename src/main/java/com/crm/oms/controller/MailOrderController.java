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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
    public CommonResult<CommonPage<MailOrder>> list(@Valid @RequestBody MailOrderParam mailOrderParam,
                                                    @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        List<MailOrder> mailOrderList = mailOrderService.list(mailOrderParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(mailOrderList));
    }


    @ApiOperation(value = "订单编辑列表")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonResult<String> list(@Valid @RequestBody List<Long> mailOrderList) {
        return CommonResult.success("订单编辑列表成功");

    }


}

