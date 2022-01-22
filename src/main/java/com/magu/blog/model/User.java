package com.magu.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM - > JAVA ( JAVA뿐만아니라 다른언어 포함 ) Object - >  테이블로 매핑해주는 기술 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!!
@Entity // User 클래스가 MySQL에 테이블이 생성된다.
//@DynamicInsert  // insert 시에 null인 필드를 제외시켜준다.
public class User {
	  
	@Id // primary key 
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. AND use-new-id-generator-mappings: falseuse-new-id-generator-mappings: false 라고 YML 에 표현되어있는데 FALSE로 해놓으면 JPA 전략 넘버링을 따라가지 않는다는 뜻
	private int id; // 시퀀스, mysql에서는 auto_increment 하도록 명시
	
	@Column(nullable = false, length = 30, unique =true)
	private String username; // 아이디 역할
	
	@Column(nullable = false, length = 100) // 123456 = > 해쉬 (비밀번호 암호화)
	private String password;
	
	@Column(nullable = false, length = 50) 
	private String email;
	
	//@ColumnDefault("'user'") // '문자' 확인해야 함 위의 최상단 어노테이션 @DynamicInsert  과 함께 사용
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다. // admin, user, manager 등 각각에 맞게 해야되는데 스트링이면 managerrrr이런식으로 잘못쓸 수 있다.
											// Enum 만든걸로 설정을해주면 - > 옆에께 강제됨  // ADMIN, USER
	
	
	@CreationTimestamp // 시간이 자동으로 입력된다.
	private java.sql.Timestamp createDate;
	/*
// yml 하위  jpa 에 관련된 것 	
//	  jpa:
//		    open-in-view: true
//		    hibernate:
//		      ddl-auto: create   //지우고 새로만드는 전략
//		      naming:
//		        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl  // 받은것을 그대로 만들어준다. 스프링 부트 형태로 가게되면 카멜 표기법이나 언더바 형태로 쓸수있다.
//		      use-new-id-generator-mappings: false      //jpa 넘버링 전략을 따라가지 않는다.
//		    show-sql: true   //  sql 에 대한 내용을 console에 표기해준다.
//		    properties:
//		      hibernate.format_sql: true // 위의 console 표기를 이쁘게 줄바꿈해준다
 * */
 
}
