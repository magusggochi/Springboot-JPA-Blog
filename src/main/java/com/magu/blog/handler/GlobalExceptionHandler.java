package com.magu.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.magu.blog.dto.ResponseDto;

@ControllerAdvice // 어디서든 오류가 발견되면 이리로 들어옴
@RestController
public class GlobalExceptionHandler {
	
//	@ExceptionHandler(value=IllegalArgumentException.class)
//	public String handleArgumentException(IllegalArgumentException e) {
//		return "<h1>" + e.getMessage() + "</h1>";
//	}
//	
//	@ExceptionHandler(value=Exception.class)
//	public String allException(Exception e) {
//		return "<h2>" + e.getMessage() + "</h2>";
//	}
	
	@ExceptionHandler(value=Exception.class)
	public ResponseDto<String> allException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()); //자바오브젝트를 JSON으로 변환해서 리턴 (JACKSON)
		// INTERNAL_SERVER_ERROR = 500ERROR
	}
	
}
