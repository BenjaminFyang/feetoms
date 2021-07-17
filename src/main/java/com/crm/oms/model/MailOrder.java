package com.crm.oms.model;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.crm.oms.common.utils.ShowMail;
import com.crm.oms.enums.DeliveryStatusEnum;
import com.crm.oms.enums.IsLockedEnum;
import com.crm.oms.enums.OrderStatusEnum;
import com.crm.oms.enums.OrderWebsiteEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
    private String orderTime;

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


    public void build0(ShowMail showMail) throws MessagingException, UnsupportedEncodingException {


        String bodyText = showMail.getBodyText();
        this.orderNumber = getOrderNumber(showMail);
        this.sku = null;
        this.productPicture = null;
        this.size = getSize(bodyText);
        this.orderWebsite = getOrderWebsite(showMail);
        this.originalPrice = new BigDecimal(getOriginalPrice(bodyText));
        this.paymentAmount = new BigDecimal(getPaymentAmount(bodyText));
        this.trackingEmail = showMail.getMailAddress("to");
        this.orderState = OrderStatusEnum.TYPE0.getCode();
        this.carrierCompany = null;
        this.address = bodyText.substring(bodyText.indexOf("Shipping to:") + 12, bodyText.indexOf("http")).replaceAll("\r\n|\r|\n", "").replaceAll(" +", "");
        this.foreignWaybillNumber = null;
        this.waybillStatus = 0;
        this.transitStatus = 0;
        this.deliveryStatus = DeliveryStatusEnum.TYPE0.getCode();
        this.orderTime = getOrderTime(bodyText);
        this.deliveryTime = null;
        this.note = OrderStatusEnum.TYPE0.getMessage();
        this.isLocked = IsLockedEnum.TYPE0.getCode();
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    @NotNull
    private String getOrderTime(String bodyText) {
        int orderDateIndex = bodyText.indexOf("Order Date");
        int index = bodyText.indexOf(", 2021");
        index = bodyText.indexOf(", 2021", index + 1);
        return bodyText.substring(orderDateIndex + 10, index).replaceAll("\r\n|\r|\n", " ").replaceAll(" +", " ") + " 2021";
    }

    private String getPaymentAmount(String bodyText) {
        return bodyText.substring(bodyText.indexOf("Card-") + 7, bodyText.indexOf("Shipping & Handling")).replaceAll("\r\n|\r|\n", "").replaceAll(" +", "");
    }

    @NotNull
    private String getOriginalPrice(String bodyText) {
        String substring = bodyText.substring(bodyText.indexOf("Subtotal") + 9, bodyText.indexOf("Gift")).replaceAll("\r\n|\r|\n", "").replaceAll(" +", "");

        System.out.println(substring);
        return substring;
    }

    private Integer getOrderWebsite(ShowMail showMail) throws MessagingException {
        String from = showMail.getFrom();
        String website = from.substring(from.indexOf("\"") + 1, from.indexOf("\"<"));
        return OrderWebsiteEnum.ofMessage(website).getCode();
    }

    @NotNull
    private String getSize(String bodyText) {
        return bodyText.substring(bodyText.indexOf("Size") + 5, bodyText.indexOf("Qty")).replaceAll("\r\n|\r|\n", " ").replaceAll(" +", " ");
    }

    @NotNull
    private String getOrderNumber(ShowMail showMail) throws MessagingException, UnsupportedEncodingException {
        // 得到订单号
        String subject = showMail.getSubject();
        return subject.substring(subject.indexOf("(") + 2, subject.indexOf(")"));
    }


}
