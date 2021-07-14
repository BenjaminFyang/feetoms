package com.crm.oms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @Email(message = "邮件格式不正确")
    @NotEmpty(message = "账号邮箱不能为空")
    private String email;

    @ApiModelProperty(value = "授权码")
    @Size(min = 1, max = 16, message = "输入授权码应该为1到16位")
    private String authorizationCode;

}
