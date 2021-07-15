package com.crm.oms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.crm.oms.dto.AddUmsAdmin;
import com.crm.oms.dto.UpdateAdminParam;
import com.crm.oms.dto.UpdateAdminPasswordParam;
import com.crm.oms.dto.UpdateUmsAdminParam;
import com.crm.oms.model.MailOrder;
import com.crm.oms.model.UmsAdmin;
import com.crm.oms.model.UmsPermission;

import java.util.List;

/**
 * 后台管理员Service
 * Created by macro on 2018/4/26.
 */
public interface UmsAdminService extends IService<UmsAdmin> {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     */
    void register(AddUmsAdmin addUmsAdmin);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);


    /**
     * 修改密码
     */
    void updatePassword(UpdateUmsAdminParam updateUmsAdminParam);


    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<UmsPermission> getPermissionList(Long adminId);


    void delete(Long adminId);


    void updateAdmin(UpdateAdminParam updateAdminParam);


}
