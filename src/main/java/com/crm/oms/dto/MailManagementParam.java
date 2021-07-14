package com.crm.oms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 账号管理同步表
 * </p>
 *
 * @author macro
 * @since 2021-07-12
 */
@Data
public class MailManagementParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮件类型 0:QQ 1:163 2:126 3:gmail")
    private Integer mailType;

    @ApiModelProperty(value = "服务类型 0:imap.163.com 1:smtp.163.com 2:pop.163.com")
    private Integer serviceType;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "授权码")
    private String authorizationCode;

}
