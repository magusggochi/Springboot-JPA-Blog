package com.magu.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 - > 응답 ( HTML 파일 ) 
// @Controller

//사용자가 요청 - > 응답 ( Data ) 

@RestController
public class HttpController {
		
	
		private static final String TAG = "HttpController : ";
	    
		//lombok 을 이용한 생성자와 게터 세터 확인
		@GetMapping("/http/lombok")
		public String lombokTest() {
			Member m = new Member(1,"ssar","1234","email");
			System.out.println(TAG + "getter : " + m.getId());
			m.setId(5000);
			System.out.println(TAG + "getter : " + m.getId());

			return "lombok test 완료";
		}
		
		//lombok 의 생성자 build를 이용한 요청
		@GetMapping("/http/lombok2")
		public String lombokTest2() {
			Member2 m = Member2.builder().username("ssar").password("1234").email("tradet@naver.com").build();
			System.out.println(TAG + "getter : " + m.getUsername());
			m.setUsername("kakao");
			System.out.println(TAG + "getter : " + m.getUsername());

			return "lombok test 완료";
		}
		
		//인터넷 브라우저 요청은 get 밖에 할 수 없다.
		//http://localhost:8080/http/get (select)
		@GetMapping("/http/get")
		public String getTest(Member m ) { //message Converter 가 해줌
			return "get  요청 : " +m.getId() + " 이름 : " + m.getUsername() + " 패스워드" +  m.getPassword() + "email : " + m.getEmail()  ;
		}

		//http://localhost:8080/http/post ( insert)
		@PostMapping("http/post") //text/plain, application/json
		public String postTest(@RequestBody Member m) { //messageConverter (스프링 부트)
			
			return "post  요청 : " +m.getId() + " 이름 : " + m.getUsername() + " 패스워드" +  m.getPassword() + "email : " + m.getEmail()  ;
		}
		
		//http://localhost:8080/http/put ( update)
		@PutMapping("/http/put")
		public String putTest(@RequestBody Member m) {
			return "put  요청 : " +m.getId() + " 이름 : " + m.getUsername() + " 패스워드" +  m.getPassword() + "email : " + m.getEmail()  ;
		}
		
		//http://localhost:8080/http/delete (delete)
		@DeleteMapping("/http/delete")
		public String deleteTest() {
			return "delete  요청";
		}
}
