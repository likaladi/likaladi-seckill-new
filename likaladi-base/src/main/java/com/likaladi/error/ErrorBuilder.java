package com.likaladi.error;


import com.likaladi.enums.BaseError;

/**
 * @author likaladi
 * 封装抛出的自定义异常
 */
public class ErrorBuilder {
    public static void throwMsg(String errorMsg){
        throw  new SocialContactException(BaseError.SYSTEM_ERROR.getErrCode(), errorMsg);
    }

    public static void throwMsg(Integer errorCode, String errorMsg){
        throw  new SocialContactException(errorCode, errorMsg);
    }

    public static void throwMsg(BaseError baseError){
        throw new SocialContactException(baseError.getErrCode(), baseError.getErrMsg());
    }

}
