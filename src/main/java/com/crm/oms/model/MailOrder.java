package com.crm.oms.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 邮件订单信息表
 * </p>
 *
 * @author fangyang
 * @since 2021-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mail_order")
@ApiModel(value = "MailOrder对象", description = "邮件订单信息表")
public class MailOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    @ApiModelProperty(value = "订单邮箱")
    private String orderEmail;

    @ApiModelProperty(value = "原SKU")
    @TableField("SKU")
    private String sku;

    @ApiModelProperty(value = "商品图片")
    private String productPicture;

    @ApiModelProperty(value = "尺码")
    private String size;

    @ApiModelProperty(value = "下单网站 0:Nike 1:Footlocker 2:Eastbay 3:Champs 4:Footaction 5:Kidsfootlocker 6:Footsites 7:Supreme 8:YeezySupply")
    private Integer orderWebsite;

    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal paymentAmount;

    @ApiModelProperty(value = "追踪邮箱")
    private String trackingEmail;

    @ApiModelProperty(value = "订单状态 0:已下单 1:取消 2:已发货 3:派件中 4:派件延迟 5:已签收 6:召回")
    private Integer orderState;

    @ApiModelProperty(value = "承运公司 0:待反馈 1:FedEx 2:UPS")
    private Integer carrierCompany;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "国外运单号")
    private String foreignWaybillNumber;

    @ApiModelProperty(value = "运单状态 0:on the way 1:arrived 2:Returned 3:Cancelled 4:Delivered 5:Transit 6:Origin Scan 7:Shipped 8:Shipment Delayed")
    private Integer waybillStatus;

    @ApiModelProperty(value = "转运确认状态 0:未操作 1:已确认 2:未确认 3:异常")
    private Integer transitStatus;

    @ApiModelProperty(value = "交付状态 0:未操作 1:回国途中 2:国内到货 3:国外已结算 4:异常")
    private Integer deliveryStatus;

    @ApiModelProperty(value = "下单时间")
    private Date orderTime;

    @ApiModelProperty(value = "发货时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "转运确认状态 0:未锁定 1:锁定")
    private Integer isLocked;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
