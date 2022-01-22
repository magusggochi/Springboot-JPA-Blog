package com.magu.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magu.blog.model.User;
import com.magu.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을해줌. IoC를 해준다.
// 서비스가 필요한 이유 
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public int userSave(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserSevice : 회원가입():" + e.getMessage());
		}
		return -1;
	}
	
	@Transactional(readOnly = true) //select 할 때트랜잭션 시작, 해당서비스가 종료될 때 트랜잭션 종료( 이때까지의 "정합성" 을 유지할 수 있다.)
	public User userLogin(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
}
