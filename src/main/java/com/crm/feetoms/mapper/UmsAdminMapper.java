package com.crm.feetoms.mapper;

import com.crm.feetoms.model.UmsAdmin;
import com.crm.feetoms.model.UmsAdminExample;
import com.crm.feetoms.model.UmsPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UmsAdminMapper {
    int countByExample(UmsAdminExample example);

    int deleteByExample(UmsAdminExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsAdmin record);

    int insertSelective(UmsAdmin record);

    List<UmsAdmin> selectByExample(UmsAdminExample example);

    UmsAdmin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsAdmin record, @Param("example") UmsAdminExample example);

    int updateByExample(@Param("record") UmsAdmin record, @Param("example") UmsAdminExample example);

    int updateByPrimaryKeySelective(UmsAdmin record);

    int updateByPrimaryKey(UmsAdmin record);


    /**
     * 获取用户信息权限
     *
     * @param adminId 操作用户的
     * @return the list of UmsPermission
     */
    @Select("SELECT\n" +
            "\tp.* \n" +
            "FROM\n" +
            "\tums_admin_role_relation ar\n" +
            "\tLEFT JOIN ums_role r ON ar.role_id = r.id\n" +
            "\tLEFT JOIN ums_role_permission_relation rp ON r.id = rp.role_id\n" +
            "\tLEFT JOIN ums_permission p ON rp.permission_id = p.id \n" +
            "WHERE\n" +
            "\tar.admin_id = #{adminId}\n" +
            "\tAND p.id IS NOT NULL")
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);

}