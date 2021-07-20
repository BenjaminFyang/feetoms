package com.crm.oms.enums;

import lombok.Getter;

import java.util.Arrays;

import static java.util.Arrays.stream;

/**
 * 险种分类
 *
 * @author fangyang
 * @since 1.0.0
 */

@Getter
public enum OrderStatusEnum {

    TYPE0(0, "已下单"),
    TYPE1(1, "取消"),
    TYPE2(2, "已发货"),
    TYPE3(3, "派件中"),
    TYPE4(4, "派件延迟"),
    TYPE5(5, "已签收"),
    TYPE6(6, "召回"),
    TYPE7(7, "异常");

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
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
        return stream(values()).anyMatch(orderStatusEnum -> orderStatusEnum.code.equals(code));
    }

    public static OrderStatusEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }
}
