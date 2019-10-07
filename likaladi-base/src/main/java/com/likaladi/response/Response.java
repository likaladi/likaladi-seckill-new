package com.likaladi.response;

import com.likaladi.enums.BaseError;

public class Response {

    /**
     * 返回成功，可以传data值
     * @param data
     * @return
     */
    public static ResponseResult ok(Object data) {
        return setResult(BaseError.OK.getErrCode(), BaseError.OK.getErrMsg(), data);
    }

    /**
     * 返回成功，沒有data值
     * @return
     */
    public static ResponseResult ok() {
        return setResult(BaseError.OK.getErrCode(), BaseError.OK.getErrMsg(), null);
    }

    /**
     * 返回成功，沒有data值
     * @param msg
     * @return
     */
    public static ResponseResult ok(String msg) {
        return setResult(BaseError.OK.getErrCode(), msg, null);
    }

    /**
     * 成功返回字符串结果
     * @param data
     * @return
     */
    public static ResponseResult okString(String data){
        return setResult(BaseError.OK.getErrCode(), BaseError.OK.getErrMsg(), data);
    }

    public static ResponseResult error(){
        return setResult(BaseError.SYSTEM_ERROR.getErrCode(), BaseError.SYSTEM_ERROR.getErrMsg(), null);
    }

    public static ResponseResult error(Integer code, String msg) {
        return setResult(code, msg, null);
    }

    /**
     * 返回错误，可以传msg
     * @param msg
     * @return
     */
    public static ResponseResult error(String msg) {
        return setResult(BaseError.SYSTEM_ERROR.getErrCode(), msg, null);
    }


    /**
     * 通用封装
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ResponseResult setResult(Integer code, String msg, Object data) {
        return new ResponseResult(code, msg, data);
    }

    /**
     * 调用数据库层判断
     * @param result
     * @return
     */
    public static Boolean toDaoResult(int result) {
        return result > 0 ? true : false;
    }

}
