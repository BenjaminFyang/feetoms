package com.crm.oms.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateUmsAdminParam {

    @ApiModelProperty(value = "id")
    @NotNull(message = "主键的id为空")
    private Long id;

    @ApiModelProperty(value = "昵称")
    @NotEmpty(message = "输入昵称为空")
    @Size(min = 1, max = 20, message = "输入昵称应该在1到20位之间")
    private String nickName;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "输入密码为空")
    @Size(min = 6, max = 16, message = "输入密码应该在6到16位之间")
    private String password;
}
