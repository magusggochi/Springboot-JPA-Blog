package com.magu.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magu.blog.model.KakaoProfile;
import com.magu.blog.model.OauthToken;
import com.magu.blog.model.User;
import com.magu.blog.service.UserService;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/**

@Controller
public class UserController {

	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
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
	
	@GetMapping("/auth/kakao/callback")
	public  String kakaoCallback(String code) {
		
		//POST 방식으로 KEY=VALUE 데이터로 요청 ( => 카카오로 )
		//httpsUrlConnection url = new HttpURLConnection() {
		//Retrofit2
		//Okhttp
		//RestTemplate
		
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "33b2b99e5c94c712bed97a7f5c718e0d");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<>(params,headers);
		
		// Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
		);
		// Gson, Json Simple, ObjectMapper
		ObjectMapper obMapper = new ObjectMapper();
		OauthToken oauthToken = null;
		try {
			 oauthToken = obMapper.readValue(response.getBody(), OauthToken.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("카카오 엑세스 토큰: " + oauthToken.getAccess_token());
		
		// 프로필 정보 받아오기 ============================================================
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+ oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
				new HttpEntity<>(headers2);
		
		// Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
		);
		
		// Gson, Json Simple, ObjectMapper
		ObjectMapper obMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = obMapper2.readValue(response2.getBody(), KakaoProfile.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//User 오브젝트 : username , password, email
		System.out.println("카카오 아이디 : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그 서버 유저네임" + kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그 서버 이메일" + kakaoProfile.getKakao_account().getEmail());
		System.out.println("블로그 서버 패스워드" + cosKey);
		
		// UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘 인데 이걸 사용해서 했더니 로그인 할 때 로그인 진행이 안됨
		//UUID garbagePassword = UUID.randomUUID();
		//System.out.println("블로그 서버 패스워드" + garbagePassword);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		// 가입자 혹은 비가입자 체크 해서 처리 
		User originUser = userService.selectUserByInfo(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
			userService.userSave(kakaoUser);
		}
		
		//로그인 처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		return "redirect:/";
	}
	
}
