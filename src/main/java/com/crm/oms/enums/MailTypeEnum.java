package com.crm.oms.enums;

import lombok.Getter;

import java.util.Arrays;

import static java.util.Arrays.stream;


@Getter
public enum MailTypeEnum {

    TYPE1(3, 1, "imaps", "imap.163.com"),
    TYPE2(3, 2, "smtp", "smtp.163.com"),
    TYPE3(3, 3, "pop", "pop.163.com");

    private Integer code;
    private Integer type;
    private String node;
    private String message;

    MailTypeEnum(Integer code, Integer type, String node, String message) {
        this.code = code;
        this.code = type;
        this.node = node;
        this.message = message;
    }

    /**
     * 校验是否是该枚举
     *
     * @param code 枚举字符串
     * @return true or false
     */
    public static boolean isValidEnum(Integer code) {
        return stream(values()).anyMatch(orderStatusEnum -> orderStatusEnum.code.equals(code));
    }

    public static MailTypeEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }

    public static MailTypeEnum ofType(Integer type) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getType().equals(type)).findFirst().orElse(null);
    }
}
