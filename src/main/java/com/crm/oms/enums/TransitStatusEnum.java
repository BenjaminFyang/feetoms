package com.crm.oms.enums;


import lombok.Getter;

import java.util.Arrays;

import static java.util.Arrays.stream;

@Getter
public enum TransitStatusEnum {

    TYPE0(0, "未操作"),
    TYPE1(1, "已确认"),
    TYPE2(2, "未确认"),
    TYPE3(3, "异常");

    private Integer code;
    private String message;

    TransitStatusEnum(Integer code, String message) {
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
        return stream(values()).anyMatch(transitStatusEnum -> transitStatusEnum.code.equals(code));
    }

    public static TransitStatusEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }

}
