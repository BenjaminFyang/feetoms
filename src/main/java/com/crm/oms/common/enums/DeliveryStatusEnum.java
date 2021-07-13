package com.crm.oms.common.enums;

import lombok.Getter;

import static java.util.Arrays.stream;

@Getter
public enum DeliveryStatusEnum {

    TYPE0(0, "未操作"),
    TYPE1(1, "回国途中"),
    TYPE2(2, "国内到货"),
    TYPE3(3, "国外已结算"),
    TYPE4(4, "异常");

    private final Integer code;
    private final String message;

    DeliveryStatusEnum(Integer code, String message) {
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
        return stream(values()).anyMatch(deliveryStatusEnum -> deliveryStatusEnum.code.equals(code));
    }
}
