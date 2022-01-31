package com.magu.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magu.blog.dto.ResponseDto;
import com.magu.blog.model.User;
import com.magu.blog.service.UserService;

@RestController // 데이터만 전달해줄꺼기 때문에
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<?> userJoin(@RequestBody User user) {
		// 실제로 DB에 INSERT를 하고 아래에서 RETURN이 되면 된다.
		// userRepository.save(user); 할수있지만 서비스를 이용한다.
		int result = userService.userSave(user);

		return new ResponseDto<Integer>(HttpStatus.OK.value(), result); // 자바오브젝트를 JSON으로 변환해서 리턴 (JACKSON)
	}
	
	@PutMapping("/auth/updateProc")
	public ResponseDto<?> update(@RequestBody User user){
		
		userService.updateUser(user);
		
		// 트랜잭션이 종료되서 db 값은 변경되었지만 세션 값을 바꾸지 않으면 회원정보가 바뀌지 않기 때문에 principal 값이 변경되지 않았다.
		// 로그아웃 후 로그인을 해야지 변경이 되기 때문에 직접 세션 값을 변경해줄 것임.
		
		//아래와 같이 내가 강제로 집어 넣는것은 안되는것을 확인했다.
//		@AuthenticationPrincipal PrincipalDetail principal,
//		HttpSession session
		
//		Authentication authentication = 
//		new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//		securityContext.setAuthentication(authentication);
//		session.setAttribute("SPIRNG_SECURITY_CONTEXT", securityContext);
		
		//그래서 다른방법을 찾아보자.
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴 (JACKSON)
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//security 쓰기 전 기본 로그인 
	//다음엔 스프링 시큐리티 이용해서 로그인 해봅세다!!!!
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user , HttpSession session, HttpServletRequest request) {
//		System.out.println("로그인 호출됨");
//		//request.getSession();
//		User principal = userService.userLogin(user); //principal (접근주체) 라는 뜻
//
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}
	
	
	
}
