package com.crm.oms.common.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * 枚举验证拦截
 */
@Slf4j
public class EnumValidatorClass implements ConstraintValidator<EnumValue, Object> {

    private Class<? extends Enum<?>> enumClass;
    private String enumMethod;
    private boolean allowNull;

    @Override
    public void initialize(EnumValue enumValue) {
        enumMethod = enumValue.enumMethod();
        enumClass = enumValue.enumClass();
        allowNull = enumValue.allowNull();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return Boolean.TRUE;
        }

        if (null == enumClass || null == enumMethod) {
            return Boolean.TRUE;
        }

        Class<?> valueClass = value.getClass();
        try {
            Method method = enumClass.getMethod(enumMethod, valueClass);
            Boolean result = (Boolean) method.invoke(null, value);
            return result == null ? Boolean.FALSE : result;
        } catch (Exception e) {
            log.error("EnumValidatorClass发生异常", e);
            return Boolean.FALSE;
        }
    }
}


