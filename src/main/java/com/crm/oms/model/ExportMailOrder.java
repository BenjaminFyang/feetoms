package com.crm.oms.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.crm.oms.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class ExportMailOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "订单编号")
    private String orderNumber;

    @ExcelProperty(value = "订单邮箱")
    private String orderEmail;

    @ExcelProperty(value = "原SKU")
    private String sku;

    @ExcelProperty(value = "尺码")
    private String size;

    @ExcelProperty(value = "下单网站")
    private Integer orderWebsite;

    @ExcelProperty(value = "原价")
    private BigDecimal originalPrice;

    @ExcelProperty(value = "付款金额")
    private BigDecimal paymentAmount;

    @ExcelProperty(value = "追踪邮箱")
    private String trackingEmail;

    @ExcelProperty(value = "订单状态")
    private String orderState;

    @ExcelProperty(value = "承运公司")
    private String carrierCompany;

    @ExcelProperty(value = "地址")
    private String address;

    @ExcelProperty(value = "国外运单号")
    private String foreignWaybillNumber;

    @ExcelProperty(value = "运单状态")
    private String waybillStatus;

    @ExcelProperty(value = "转运确认状态")
    private String transitStatus;

    @ExcelProperty(value = "交付状态")
    private String deliveryStatus;

    @ExcelProperty(value = "下单时间")
    private Date orderTime;

    @ExcelProperty(value = "发货时间")
    private Date deliveryTime;

    @ExcelProperty(value = "更新时间")
    private Date updateTime;


    public void build(MailOrder mailOrder) {
        this.orderState = OrderStatusEnum.of(mailOrder.getOrderState()).getMessage();
        this.carrierCompany = CarrierCompanyEnum.of(mailOrder.getCarrierCompany()).getMessage();
        this.waybillStatus = WaybillStatusEnum.of(mailOrder.getWaybillStatus()).getMessage();
        this.transitStatus = TransitStatusEnum.of(mailOrder.getTransitStatus()).getMessage();
        this.deliveryStatus = DeliveryStatusEnum.of(mailOrder.getDeliveryStatus()).getMessage();
    }

}
