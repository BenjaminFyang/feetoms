package com.crm.oms.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class AddUmsAdmin {

    @NotEmpty(message = "用户名不能为空")
    @Size(min = 1, max = 16, message = "输入用户名应该在1到20位之间")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotEmpty(message = "昵称不能为空")
    @Size(min = 1, max = 16, message = "输入昵称应该在1到20位之间")
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @Email(message = "邮件格式不正确")
    @NotEmpty(message = "账号邮箱不能为空")
    @ApiModelProperty(value = "账号邮箱")
    private String email;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 1, max = 16, message = "输入密码应该在6到16位之间")
    @ApiModelProperty(value = "密码")
    private String password;

    @Min(value = 1, message = "邮件个数不能为负数")
    @NotNull(message = "邮箱个数上限不能为空")
    @ApiModelProperty(value = "邮箱个数上限")
    private Integer numberMailbox;

    @NotNull(message = "到期时间不能为空")
    @Future(message = "输入的过期时间不应该是将来的时间")
    @ApiModelProperty(value = "到期时间")
    private Date dueDate;

}
