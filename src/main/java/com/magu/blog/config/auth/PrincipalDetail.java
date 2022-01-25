package com.magu.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.magu.blog.model.User;

import lombok.Data;
import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 
// 오브젝트를 스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다.
@Getter
public class PrincipalDetail implements UserDetails{
	 
	private User user; //컴포지션이라고 부른다. 이렇게 객체를 가지고 있는것

	public PrincipalDetail(User user) {
		this.user = user;
	}

	// 계정이 어떤 권한을 가졌는지 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
//		collectors.add(new GrantedAuthority() {
//			@Override
//			public String getAuthority() {
//				return "ROLE_"+user.getRole(); //스프링 롤의 PREFIX 규칙 "ROLE_" 규칙 예 ) ROLE_USER
//			}
//		});
		//자바는 오브젝트를 받을 순 있지만 일반 순수한 메서드를 그냥 통째로 넘길 순 없다. 
		//그래서 오브젝트로 감싸서(new GrantedAuthority()) 위에 처럼 보냈다. 람다식으로 표현 아래에 작성해 보겠다.
		
		collectors.add(()->{return "ROLE_"+user.getRole();});
		
		return collectors;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정이 만료되지 않았는지 리턴한다. (true :만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠기지 않았는지 리턴한다. (true : 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴한다 ( true : 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정 활성화(사용가능)인지 리턴한다. (true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}

	
}
