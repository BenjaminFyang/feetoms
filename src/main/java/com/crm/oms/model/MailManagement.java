package com.crm.oms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.crm.oms.dto.MailManagementParam;
import com.crm.oms.enums.PassNotEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 账号管理同步表
 * </p>
 *
 * @author macro
 * @since 2021-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mail_management")
@ApiModel(value = "MailManagement对象", description = "账号管理同步表")
public class MailManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "邮件类型 0:QQ 1:163 2:126 3:gmail")
    private Integer mailType;

    @ApiModelProperty(value = "服务类型 0:imap.163.com 1:smtp.163.com 2:pop.163.com")
    private Integer serviceType;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "授权码")
    private String authorizationCode;

    @ApiModelProperty(value = "测试是否通过 0:通过 1:不通过")
    private Integer passNot;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    public MailManagement() {
    }

    public MailManagement(MailManagementParam mailManagementParam) {
        this.mailType = mailManagementParam.getMailType();
        this.serviceType = mailManagementParam.getServiceType();
        this.email = mailManagementParam.getEmail();
        this.authorizationCode = mailManagementParam.getAuthorizationCode();
        this.passNot = PassNotEnum.TYPE0.getCode();
        this.createTime = new Date();
        this.updateTime = new Date();
    }
}
