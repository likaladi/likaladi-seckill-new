package com.likaladi.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author likaladi
 *自定义范围入参校验
 */
//注解是指定当前自定义注解可以使用在哪些地方，这里仅仅让他可以使用在方法上和属性上；
@Target({ElementType.METHOD,ElementType.FIELD})
//指定当前注解保留到运行时；
@Retention(RetentionPolicy.RUNTIME)
//指定了当前注解使用哪个类来进行校验。
@Constraint(validatedBy = RangeValidator.class)
public @interface Range {

    String value() default "";

    String message() default"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
