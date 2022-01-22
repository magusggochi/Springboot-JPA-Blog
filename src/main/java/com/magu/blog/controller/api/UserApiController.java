package com.magu.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magu.blog.dto.ResponseDto;
import com.magu.blog.model.RoleType;
import com.magu.blog.model.User;
import com.magu.blog.service.UserService;

@RestController // 데이터만 전달해줄꺼기 때문에
public class UserApiController {

	@Autowired
	private UserService userService;

	@PostMapping("/api/user")
	public ResponseDto<?> userJoin(@RequestBody User user) {
		System.out.println("UserApiController: 저장완료");

		// 실제로 DB에 INSERT를 하고 아래에서 RETURN이 되면 된다.
		// userRepository.save(user); 할수있지만 서비스를 이용한다.
		user.setRole(RoleType.USER);
		int result = userService.userSave(user);

		return new ResponseDto<Integer>(HttpStatus.OK.value(), result); // 자바오브젝트를 JSON으로 변환해서 리턴 (JACKSON)
	}

	//다음엔 스프링 시큐리티 이용해서 로그인 해봅세다!!!!
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user , HttpSession session) {
		System.out.println("로그인 호출됨");
		User principal = userService.userLogin(user); //principal (접근주체) 라는 뜻

		if(principal != null) {
			session.setAttribute("principal", principal);
		}
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}