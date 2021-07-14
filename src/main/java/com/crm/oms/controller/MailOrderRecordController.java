package com.crm.oms.controller;


import com.crm.oms.common.api.CommonPage;
import com.crm.oms.common.api.CommonResult;
import com.crm.oms.model.MailOrderRecord;
import com.crm.oms.service.MailOrderRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public CommonResult<CommonPage<MailOrderRecord>> list() {
        List<MailOrderRecord> mailOrderList = mailOrderRecordService.list();
        return CommonResult.success(CommonPage.restPage(mailOrderList));
    }

}

