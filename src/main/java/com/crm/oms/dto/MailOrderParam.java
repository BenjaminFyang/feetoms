package com.crm.oms.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.crm.oms.common.validator.EnumValue;
import com.crm.oms.enums.CarrierCompanyEnum;
import com.crm.oms.enums.OrderWebsiteEnum;
import com.crm.oms.model.MailOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 邮件订单信息表
 * </p>
 *
 * @author fangyang
 * @since 2021-07-12
 */
@Data
public class MailOrderParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    @ApiModelProperty(value = "原SKU")
    private String sku;

    @ApiModelProperty(value = "追踪邮箱")
    private String trackingEmail;

    @EnumValue(enumClass = CarrierCompanyEnum.class, message = "订单对应的状态不存在")
    @ApiModelProperty(value = "订单状态 0:已下单 1:取消 2:已发货 3:派件中 4:派件延迟 5:已签收 6:召回")
    private Integer orderState;

    @EnumValue(enumClass = OrderWebsiteEnum.class, message = "订单对应的状态不存在")
    @ApiModelProperty(value = "下单网站 0:Nike 1:Footlocker 2:Eastbay 3:Champs 4:Footaction 5:Kidsfootlocker 6:Footsites 7:Supreme 8:YeezySupply")
    private Integer orderWebsite;

    @ApiModelProperty(value = "国外运单号")
    private String foreignWaybillNumber;

    @ApiModelProperty(value = "开始下单时间")
    private Date startOrderTime;

    @ApiModelProperty(value = "结束下单时间")
    private Date endOrderTime;

    @ApiModelProperty(value = "开始发货时间")
    private Date startDeliveryTime;

    @ApiModelProperty(value = "结束发货时间")
    private Date endDeliveryTime;


    public MailOrderParam() {
    }

    /**
     * 订单信息列表查询数据封装.
     *
     * @return the LambdaQueryWrapper
     */
    public LambdaQueryWrapper<MailOrder> list() {
        LambdaQueryWrapper<MailOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        return lambdaQueryWrapper.eq(Objects.nonNull(orderNumber), MailOrder::getOrderNumber, orderNumber)
                .eq(Objects.nonNull(sku), MailOrder::getSku, sku)
                .eq(Objects.nonNull(trackingEmail), MailOrder::getTrackingEmail, trackingEmail)
                .eq(Objects.nonNull(orderState), MailOrder::getOrderState, orderState)
                .eq(Objects.nonNull(orderWebsite), MailOrder::getOrderWebsite, orderWebsite)
                .eq(Objects.nonNull(foreignWaybillNumber), MailOrder::getForeignWaybillNumber, foreignWaybillNumber)
                .between(Objects.nonNull(startOrderTime) && Objects.nonNull(endOrderTime), MailOrder::getCreateTime, startOrderTime, endOrderTime)
                .between(Objects.nonNull(startDeliveryTime) && Objects.nonNull(endDeliveryTime), MailOrder::getDeliveryTime, startDeliveryTime, endDeliveryTime);
    }
}
