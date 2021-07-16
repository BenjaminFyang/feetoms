package com.crm.oms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crm.oms.common.api.CommonPage;
import com.crm.oms.common.api.CommonResult;
import com.crm.oms.dto.MailManagementEditParam;
import com.crm.oms.dto.MailManagementParam;
import com.crm.oms.model.MailManagement;
import com.crm.oms.model.UmsAdmin;
import com.crm.oms.service.MailManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 账号管理同步表 前端控制器
 *
 * @since 2021-07-12
 */

@RestController
@Api(tags = "账号同步")
@RequestMapping("/mailManagement")
public class MailManagementController {

    @Resource
    private MailManagementService mailManagementService;

    @ApiOperation(value = "1、账号同步列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult<CommonPage<MailManagement>> list(@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<MailManagement> page = new Page<>(pageNum, pageSize);
        Page<MailManagement> mailManagementPage = mailManagementService.page(page);
        return CommonResult.success(CommonPage.restPage(mailManagementPage));
    }

    @ApiOperation(value = "2、账号同步新增")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public CommonResult<String> insert(@Valid @RequestBody MailManagementParam mailManagementParam) {
        mailManagementService.insert(mailManagementParam);
        return CommonResult.success("账号同步新增成功");
    }

    @ApiOperation(value = "3、账号同步编辑")
    @RequestMapping(value = "/edit/{mailManagementId}", method = RequestMethod.POST)
    public CommonResult<String> edit(@Valid @RequestBody MailManagementEditParam mailManagementEditParam) {
        mailManagementService.edit(mailManagementEditParam);
        return CommonResult.success("账号同步编辑成功");
    }

    @ApiOperation(value = "4、账号同步删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult<String> delete(@Valid @RequestBody List<Long> mailManagementIdList) {
        mailManagementService.delete(mailManagementIdList);
        return CommonResult.success("账号同步删除成功");
    }


    @ApiOperation(value = "5、账号测试是否通过")
    @RequestMapping(value = "/isPass/{mailManagementId}", method = RequestMethod.POST)
    public CommonResult<String> isPass(@PathVariable Long mailManagementId) {
        mailManagementService.isPass(mailManagementId);
        return CommonResult.failed("邮箱设置存在异常，请检查授权码是否输入正确，或请手动登陆邮箱查看IMAP/SMTP服务是否正确开启");
    }
}

