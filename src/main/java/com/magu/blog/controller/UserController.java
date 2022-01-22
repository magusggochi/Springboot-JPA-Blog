package com.magu.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping({"''","/"})
	public  String index() {
		//WEB-INF/views/index.jsp
		System.out.println("hi Magu");
		return "index";
	}
	
	@GetMapping("/user/joinForm")
	public String joinForm() {
		System.out.println("hi");
		//WEB-INF/view/joinForm.jsp
		return "user/joinForm";
	}
	
	@GetMapping("/user/loginForm")
	public String loginForm() {
		//System.out.println("hi");
		//WEB-INF/view/joinForm.jsp
		return "user/loginForm";
	}
}
