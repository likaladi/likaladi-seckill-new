package com.likaladi.error;

import lombok.Data;

/**
 * @author likaladi
 * 自定义抛出异常
 */
@Data
public class SocialContactException extends RuntimeException {

	private Integer code;//自定义异常码

	public SocialContactException(Integer code, String message) {
		// 父类的构造函数；调用底层的Throwable的构造函数，将参数message赋值到detailMessage (Throwable的属性)
		super(message);
		this.code = code;//赋值code码
	}

}
