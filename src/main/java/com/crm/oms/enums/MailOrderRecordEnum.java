package com.crm.oms.enums;

import lombok.Getter;

import java.util.Arrays;

import static java.util.Arrays.stream;


@Getter
public enum MailOrderRecordEnum {

    TYPE0(0, "订单状态", "已下单"),
    TYPE1(1, "订单状态", "取消"),
    TYPE2(2, "订单状态", "已发货"),
    TYPE3(3, "订单状态", "派件中"),
    TYPE4(4, "订单状态", "派件延迟"),
    TYPE5(5, "订单状态", "已签收"),
    TYPE6(6, "订单状态", "召回");

    private Integer code;
    private String node;
    private String message;

    MailOrderRecordEnum(Integer code, String node, String message) {
        this.code = code;
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

    public static MailOrderRecordEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }
}
