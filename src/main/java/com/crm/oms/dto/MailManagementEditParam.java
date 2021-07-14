package com.crm.oms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class MailManagementEditParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "主键的id为空")
    @ApiModelProperty(value = "主键的id")
    private Long mailManagementId;

    @NotNull(message = "邮件类型不能为空")
    @ApiModelProperty(value = "邮件类型 0:QQ 1:163 2:126 3:gmail")
    private Integer mailType;

    @NotNull(message = "服务类型不能为空")
    @ApiModelProperty(value = "服务类型 0:imap.163.com 1:smtp.163.com 2:pop.163.com")
    private Integer serviceType;

    @Email(message = "邮件格式不正确")
    @NotNull(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @NotEmpty(message = "授权码不能为空")
    @ApiModelProperty(value = "授权码")
    private String authorizationCode;

}
