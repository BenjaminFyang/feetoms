package com.crm.oms.enums;


import lombok.Getter;

import static java.util.Arrays.stream;

@Getter
public enum IsLockedEnum {

    TYPE0(0, "未锁定"),
    TYPE1(1, "锁定");

    private Integer code;
    private String message;

    IsLockedEnum(Integer code, String message) {
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
        return stream(values()).anyMatch(isLockedEnum -> isLockedEnum.code.equals(code));
    }
}
