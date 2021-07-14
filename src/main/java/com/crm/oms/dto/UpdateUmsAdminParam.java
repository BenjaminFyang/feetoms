package com.crm.oms.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUmsAdminParam {

    @ApiModelProperty(value = "id")
    @NotNull(message = "主键的id为空")
    private Long id;

    @ApiModelProperty(value = "昵称")
    @NotNull(message = "输入昵称为空")
    private String nickName;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "输入密码为空")
    private String password;
}
