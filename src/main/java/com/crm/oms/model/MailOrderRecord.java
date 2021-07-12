package com.crm.oms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 邮件订单操作记录信息表
 * </p>
 *
 * @author fangyang
 * @since 2021-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mail_order_record")
@ApiModel(value = "MailOrderRecord对象", description = "邮件订单操作记录信息表")
public class MailOrderRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "邮件订单信息表主键id")
    private Long mailOrderId;

    @ApiModelProperty(value = "姓名")
    private String username;

    @ApiModelProperty(value = "账号邮箱")
    private String email;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
