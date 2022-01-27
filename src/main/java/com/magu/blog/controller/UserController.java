package com.magu.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/**

@Controller
public class UserController {
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		//WEB-INF/view/joinForm.jsp
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		//WEB-INF/view/joinForm.jsp
		return "user/loginForm";
	}
	
	@GetMapping("/auth/updateForm")
	public String updateForm() {
		//WEB-INF/view/updateForm.jsp
		return "user/updateForm";
	}
	
}
