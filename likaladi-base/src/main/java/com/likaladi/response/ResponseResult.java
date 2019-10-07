package com.likaladi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {

    private Integer code; // 返回码

    private String msg;  //返回信息

    private T data;     // 返回数据

}