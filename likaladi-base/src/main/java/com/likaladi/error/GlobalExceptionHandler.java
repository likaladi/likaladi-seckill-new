package com.likaladi.error;

import com.likaladi.response.Response;
import com.likaladi.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likaladi
 * 异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	/**
	 * 捕获全局异常，处理所有不可知的异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseResult<Void> exceptionHandler(Exception ex) {
		log.error("###全局捕获异常###,error:", ex);
		return Response.error();
	}


	/**
	 * 自定义异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(SocialContactException.class)
	public ResponseResult<Void> handleMyException(SocialContactException e) {
		log.error("###自定义捕获异常###,error:", e);
		return Response.error(e.getCode(), e.getMessage());
	}

	/**
	 * 参数校验异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseResult<Void> exceptionHandler(MethodArgumentNotValidException ex) {
		log.error("###参数校验异常###,error:{}", ex);

		BindingResult bindingResult = ex.getBindingResult();

		return Response.error(bindingResult.getFieldError().getDefaultMessage());
	}

}
