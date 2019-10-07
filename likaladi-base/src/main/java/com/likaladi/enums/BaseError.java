package com.likaladi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 通用错误码定义
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum BaseError {

    OK(200, "成功"),
    SYSTEM_ERROR(500,"系统错误"),

    //通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),
    TOKEN_NOT_NULL(10003,"TOKEN不能为空"),
    TOKEN_INVALID(10004, "TOKEN无效"),

    //20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或密码不正确"),
    LOGINERROR(20003, "用户名或密码错误"),
    USER_NOT_LOGIN(20004,"用户还未登陆"),
    ACCESSERROR(20005, "权限不足"),
    REMOTEERROR(20006, "远程调用失败"),
    REPERROR(20007, "重复操作"),

    //30000开头为交易信息错误定义
    STOCK_NOT_ENOUGH(30001,"库存不足"),

    ;


    private int errCode;
    private String errMsg;


}
