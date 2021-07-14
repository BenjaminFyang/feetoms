package com.crm.oms.dto;


import com.crm.oms.common.validator.EnumValue;
import com.crm.oms.enums.DeliveryStatusEnum;
import com.crm.oms.enums.WaybillStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MailOrderEditParam {

    @ApiModelProperty(value = "订单主键的id的集合")
    @NotNull(message = "订单主键的id的集合为空")
    private List<Long> mailOrderIdList;

    @ApiModelProperty(value = "转运状态")
    @EnumValue(enumClass = WaybillStatusEnum.class, message = "转运的状态不存在")
    @NotNull(message = "转运状态为空")
    private Integer waybillStatus;

    @ApiModelProperty(value = "交付状态")
    @EnumValue(enumClass = DeliveryStatusEnum.class, message = "交付的状态不存在")
    @NotNull(message = "交付状态为空")
    private Integer deliveryStatus;

    @ApiModelProperty(value = "备注信息")
    @NotEmpty(message = "备注信息不能为空")
    private String note;
}
