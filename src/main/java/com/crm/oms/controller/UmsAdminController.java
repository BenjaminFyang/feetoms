package com.crm.oms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crm.oms.common.api.CommonPage;
import com.crm.oms.common.api.CommonResult;
import com.crm.oms.common.utils.TransmittableThreadLocalContext;
import com.crm.oms.dto.AddUmsAdmin;
import com.crm.oms.dto.UmsAdminLoginParam;
import com.crm.oms.dto.UpdateAdminParam;
import com.crm.oms.dto.UpdateUmsAdminParam;
import com.crm.oms.model.UmsAdmin;
import com.crm.oms.model.UmsPermission;
import com.crm.oms.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台用户管理
 */


@RestController
@Api(tags = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {

    @Resource
    private UmsAdminService adminService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<Map<String, String>> login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {

        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前用户的登陆信息")
    @RequestMapping(value = "/getInformation", method = RequestMethod.POST)
    public CommonResult<UmsAdmin> getInformation() {
        return CommonResult.success(TransmittableThreadLocalContext.getAuthDataBo());
    }

    @ApiOperation("获取用户所有权限（包括+权限）")
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public CommonResult<List<UmsPermission>> getPermissionList() {
        UmsAdmin umsAdmin = TransmittableThreadLocalContext.getAuthDataBo();
        List<UmsPermission> permissionList = adminService.getPermissionList(umsAdmin.getId());
        return CommonResult.success(list2Tree(permissionList));
    }

    @ApiOperation("修改个人用户信息")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public CommonResult<String> updatePassword(@Validated @RequestBody UpdateUmsAdminParam updatePasswordParam) {
        adminService.updatePassword(updatePasswordParam);
        return CommonResult.success("修改用户密码成功");
    }

    @ApiOperation(value = "账户管理列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult<CommonPage<UmsAdmin>> list(@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsAdmin> page = new Page<>(pageNum, pageSize);
        Page<UmsAdmin> pageResult = adminService.page(page);
        return CommonResult.success(CommonPage.restPage(pageResult));
    }

    @ApiOperation(value = "添加用户")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<String> register(@Validated @RequestBody AddUmsAdmin addUmsAdmin) {
        adminService.register(addUmsAdmin);
        return CommonResult.success("添加用户成功");
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/delete/{adminId}", method = RequestMethod.GET)
    public CommonResult<String> delete(@PathVariable Long adminId) {
        adminService.delete(adminId);
        return CommonResult.success("删除用户成功");
    }

    @ApiOperation(value = "修改账户信息")
    @RequestMapping(value = "/updateAdmin", method = RequestMethod.POST)
    public CommonResult<String> updateAdmin(@Validated @RequestBody UpdateAdminParam updateAdminParam) {
        adminService.updateAdmin(updateAdminParam);
        return CommonResult.success("修改账户信息成功");
    }


    public static List<UmsPermission> list2Tree(List<UmsPermission> treeNodeList) {
        Map<Long, List<UmsPermission>> nodeByParentIdMap = treeNodeList.stream().collect(Collectors.groupingBy(UmsPermission::getPid));
        return treeNodeList.stream().filter(treeNode -> treeNode.getId().equals(0L))
                .peek(node -> node.setChildren(nodeByParentIdMap.get(node.getId()))).collect(Collectors.toList());
    }

}
