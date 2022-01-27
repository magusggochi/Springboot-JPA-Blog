package com.magu.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magu.blog.model.RoleType;
import com.magu.blog.model.User;
import com.magu.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을해줌. IoC를 해준다.
// 서비스가 필요한 이유 
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional
	public int userSave(User user) {
		try {
			
			String rawPassword = user.getPassword();
			String encPassWORD = encoder.encode(rawPassword); //해쉬
			user.setPassword(encPassWORD);
			user.setRole(RoleType.USER);
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserSevice : 회원가입():" + e.getMessage());
		}
		return -1;
	}
	
	@Transactional
	public int updateUser(User user) {
		try {
			// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화 시키고, 영속화 된 User 오브젝트를 수정
			User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
				return new IllegalArgumentException("회원 찾기 실패");
			});
			String newPassword = encoder.encode(user.getPassword());
			persistance.setPassword(newPassword);
			persistance.setEmail(user.getEmail());
			// 회원수정 함수 종료시 = 서비스 종료시 = 트랜잭션이 종료된다 = commit 이 자동으로 된다 = flush가 된다.
			// 영속화 된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserSevice : 회원가입():" + e.getMessage());
		}
		return -1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//security 쓰기 전 기본 로그인 	
//	@Transactional(readOnly = true) //select 할 때트랜잭션 시작, 해당서비스가 종료될 때 트랜잭션 종료( 이때까지의 "정합성" 을 유지할 수 있다.)
//	public User userLogin(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}
