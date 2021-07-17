package com.crm.oms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crm.oms.common.api.CommonPage;
import com.crm.oms.common.api.CommonResult;
import com.crm.oms.model.MailOrder;
import com.crm.oms.model.MailOrderRecord;
import com.crm.oms.service.MailOrderRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 邮件订单操作记录信息表 前端控制器
 *
 * @author fangyang
 * @since 2021-07-12
 */

@Api(tags = "邮件订单操作日志")
@RestController
@RequestMapping("/mailOrderRecord")
public class MailOrderRecordController {

    @Resource
    private MailOrderRecordService mailOrderRecordService;

    @ApiOperation(value = "1、邮件订单操作日志列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult<CommonPage<MailOrderRecord>> list(
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "mailOrder") Long mailOrder) {

        Page<MailOrderRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<MailOrderRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<MailOrderRecord> orderRecordLambdaQueryWrapper = lambdaQueryWrapper.eq(MailOrderRecord::getMailOrderId, mailOrder);
        Page<MailOrderRecord> orderRecordPage = mailOrderRecordService.page(page, orderRecordLambdaQueryWrapper);
        return CommonResult.success(CommonPage.restPage(orderRecordPage));
    }

}

