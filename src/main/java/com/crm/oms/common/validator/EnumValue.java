package com.crm.oms.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证状态是否在指定范围内的注解
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidatorClass.class)
public @interface EnumValue {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return 枚举类
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * @return 枚举校验的方法
     */
    String enumMethod() default "isValidEnum";

    /**
     * @return 默认提示文字
     */
    String message() default "传参错误,对应枚举未找到";

    /**
     * @return 默认返回为false
     */
    boolean allowNull() default false;


}
