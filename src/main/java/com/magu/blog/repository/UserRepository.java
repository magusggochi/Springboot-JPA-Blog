package com.magu.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magu.blog.model.User;

// DAO
// 스프링 레거시 쓰던사람은 BEAN이 생성되나요 ?
// 예전엔 @Repository //  생략가능하다.
// JPA는 자동으로 빈으로 등록
public interface UserRepository extends JpaRepository<User, Integer> {

	
}
