package com.crm.oms.enums;


import lombok.Getter;

import java.util.Arrays;

import static java.util.Arrays.stream;

@Getter
public enum PassNotEnum {

    TYPE0(0, "通过"),
    TYPE1(1, "不通过");

    private Integer code;
    private String message;

    PassNotEnum(Integer code, String message) {
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


    public static PassNotEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }
}
