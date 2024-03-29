package com.crm.oms.enums;

import lombok.Getter;

import java.util.Arrays;

import static java.util.Arrays.stream;


@Getter
public enum OrderWebsiteEnum {

    TYPE0(0, "Nike.com"),
    TYPE1(1, "Footlocker"),
    TYPE2(2, "Eastbay"),
    TYPE3(3, "Champs"),
    TYPE4(4, "Footaction"),
    TYPE5(5, "Kidsfootlocker"),
    TYPE6(6, "Footsites"),
    TYPE7(6, "Supreme"),
    TYPE8(6, "YeezySupply");

    private Integer code;
    private String message;

    OrderWebsiteEnum(Integer code, String message) {
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
        return stream(values()).anyMatch(orderWebsiteEnum -> orderWebsiteEnum.code.equals(code));
    }

    public static OrderWebsiteEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }

    public static OrderWebsiteEnum ofMessage(String message) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getMessage().equals(message)).findFirst().orElse(null);
    }
}
