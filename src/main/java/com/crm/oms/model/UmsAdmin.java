package com.crm.oms.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class UmsAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "账号邮箱")
    private String email;

    private String password;

    private Integer role;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "邮箱个数上限")
    private Integer numberMailbox;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "过期时间")
    private Date dueDate;

    @ApiModelProperty(value = "最后登录时间")
    private Date loginTime;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer status;


    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", username=" + username +
                ", password=" + password +
                ", icon=" + icon +
                ", email=" + email +
                ", nickName=" + nickName +
                ", note=" + note +
                ", createTime=" + createTime +
                ", loginTime=" + loginTime +
                ", status=" + status +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}