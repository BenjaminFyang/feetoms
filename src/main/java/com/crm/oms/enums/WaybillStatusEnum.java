package com.crm.oms.enums;


import lombok.Getter;

import java.util.Arrays;

import static java.util.Arrays.stream;

@Getter
public enum WaybillStatusEnum {

    TYPE0(0, "on the way"),
    TYPE1(1, "arrived"),
    TYPE2(2, "Returned"),
    TYPE3(3, "Cancelled"),
    TYPE4(4, "Delivered"),
    TYPE5(5, "Transit"),
    TYPE6(6, "Scan"),
    TYPE7(7, "Shipped"),
    TYPE8(8, "Shipment Delayed");

    private Integer code;
    private String message;

    WaybillStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 校验是否是该枚举
     *
     * @param code 枚举字符串
     * @return true or false
     */
    public static boolean isValidEnum(Integer code) {
        return stream(values()).anyMatch(waybillStatusEnum -> waybillStatusEnum.code.equals(code));
    }


    public static WaybillStatusEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }
}
