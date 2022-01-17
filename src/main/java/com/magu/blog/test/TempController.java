package com.magu.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//동작원리에 대한 기술

@Controller
public class TempController {

		// http://localhost:8000/blog/temp/home
		@GetMapping("/temp/home")
		public String tempHome() {
			System.out.println("tempHome()");
			
			//파일리턴 기본경로 : src/main/resources/static
			//리턴명 : /home.html
			//풀경로 : src/main/resources/static/home.html
			return "/home.html";
			
		}
		
		@GetMapping("/temp/img")
		public String tempImg() {
			
			return "/a.png";
		}
		
		
		@GetMapping("/temp/jsp")
		public String tempJsp() {
			//      prefix: /WEB-INF/views/
		    //      suffix: .jsp
			return "test";
		}
}
