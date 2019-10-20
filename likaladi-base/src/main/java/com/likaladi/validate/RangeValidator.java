package com.likaladi.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author likaladi
 * @ClassName RangeValidator
 * @Description  校验注解的校验逻辑
 * @Date 2019/3/29 17:23
 **/
public class RangeValidator implements ConstraintValidator<Range, Object>{

    /**
     * 注解输入值
     */
    private String value;

    /**
     * @Description 校验前的初始化工作
     * @param range
     */
    @Override
    public void initialize(Range range) {
        value = range.value();
    }

    /**
     * @Description 具体的校验逻辑
     * @param o
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        if(Objects.isNull(o)){
            return false;
        }

        boolean temp = false;
        String[] values = value.split(",");
        if(o instanceof String){
            for(String str : values){
                if(Objects.equals(str, o)){
                    temp = true;
                    break;
                }
            }
            return temp;
        }
        if(o instanceof Integer){
            for(String str : values){
                if(Objects.equals(Integer.parseInt(str), o)){
                    temp = true;
                    break;
                }
            }
            return temp;
        }
        return true;
    }
}
