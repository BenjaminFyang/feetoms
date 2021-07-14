package com.crm.oms.controller;


import com.alibaba.excel.EasyExcel;
import com.crm.oms.common.api.CommonPage;
import com.crm.oms.common.api.CommonResult;
import com.crm.oms.common.utils.ConvertUtil;
import com.crm.oms.dto.MailOrderEditParam;
import com.crm.oms.dto.MailOrderParam;
import com.crm.oms.model.ExportMailOrder;
import com.crm.oms.model.MailOrder;
import com.crm.oms.service.MailOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private MailOrderService mailOrderService;
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd(HH-mm-ss)");

    @ApiOperation(value = "订单信息列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult<CommonPage<MailOrder>> list(@Valid @RequestBody MailOrderParam mailOrderParam,
                                                    @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        List<MailOrder> mailOrderList = mailOrderService.list(mailOrderParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(mailOrderList));
    }


    @ApiOperation(value = "订单导出")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public CommonResult<String> export(HttpServletResponse response) throws IOException {

        MailOrderParam mailOrderParam1 = new MailOrderParam();
        mailOrderParam1.setOrderNumber("122");

        List<MailOrder> mailOrderList = mailOrderService.export(mailOrderParam1);
        List<ExportMailOrder> exportMailOrderList = mailOrderList.stream()
                .map(mailOrder -> {
                    ExportMailOrder exportMailOrder = ConvertUtil.convert(mailOrder, ExportMailOrder.class);
                    exportMailOrder.build(mailOrder);
                    return exportMailOrder;
                }).collect(Collectors.toList());

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        // 保证下载到本地文件名不乱码的
        String fileName = URLEncoder.encode("订单明细表" + FORMATTER.format(new Date()), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        // List<模型> 的数据体，替换成自己的业务代码即可
        EasyExcel.write(response.getOutputStream(), ExportMailOrder.class).sheet("sheet").doWrite(exportMailOrderList);

        return CommonResult.success("");
    }


    @ApiOperation(value = "订单编辑")
    @RequestMapping(value = "/editMailOrder", method = RequestMethod.POST)
    public CommonResult<String> editMailOrder(@Valid @RequestBody MailOrderEditParam mailOrderEditParam) {

        mailOrderService.editMailOrder(mailOrderEditParam);
        return CommonResult.success("订单编辑列表成功");
    }

    @ApiOperation(value = "订单锁定")
    @RequestMapping(value = "/lockingMailOrder/{mailOrderId}", method = RequestMethod.GET)
    public CommonResult<String> lockingMailOrder(@PathVariable Long mailOrderId) {
        mailOrderService.lockingMailOrder(mailOrderId);
        return CommonResult.success("订单锁定成功");
    }

    @ApiOperation(value = "订单锁定解除")
    @RequestMapping(value = "/relieveMailOrder/{mailOrderId}", method = RequestMethod.POST)
    public CommonResult<String> relieveMailOrder(@PathVariable Long mailOrderId) {
        mailOrderService.relieveMailOrder(mailOrderId);
        return CommonResult.success("订单锁定成功");
    }

    @ApiOperation(value = "订单删除")
    @RequestMapping(value = "/deleteMailOrder/{mailOrderId}", method = RequestMethod.POST)
    public CommonResult<String> deleteMailOrder(@PathVariable Long mailOrderId) {
        mailOrderService.deleteMailOrder(mailOrderId);
        return CommonResult.success("订单锁定成功");
    }

}

