package com.crm.oms.enums;


import lombok.Getter;

import static java.util.Arrays.stream;

@Getter
public enum CarrierCompanyEnum {

    TYPE0(0, "待反馈"),
    TYPE1(1, "FedEx"),
    TYPE2(2, "UPS");

    private Integer code;
    private String message;

    CarrierCompanyEnum(Integer code, String message) {
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
        return stream(values()).anyMatch(carrierCompanyEnum -> carrierCompanyEnum.code.equals(code));
    }
}
