package com.magu.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.magu.blog.model.User;

// DAO
// 스프링 레거시 쓰던사람은 BEAN이 생성되나요 ?
// 예전엔 @Repository //  생략가능하다.
// JPA는 자동으로 빈으로 등록
public interface UserRepository extends JpaRepository<User, Integer> {
	
	//SELECT * FROM user WHERE username =1?;
	Optional<User> findByUsername(String username);
	
	
	
	
	
	
	
	
	
	
	
	//security 쓰기 전 기본 로그인 
	//JPA Naming 쿼리전략
	// 아래의 함수는 없는데 아래와 같이 만들면 , 이름은 대문자로 꺾이는 부분
	// SELECT * FROM user WHERE username= ? AND Password = ?; 
//	User findByUsernameAndPassword(String username, String password);
	
	//위와 같이 만들거나 아래와 같이 만들수 있따. 네이티브 쿼리를 쓸 수 있따.
//	@Query(value ="SELECT * FROM user WHERE username= ?1 AND Password = ?2", nativeQuery=true)
//	User login(String usernaem, String password);
}
