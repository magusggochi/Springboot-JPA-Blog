package com.magu.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.magu.blog.config.auth.PrincipalDetail;
import com.magu.blog.config.auth.PrincipalDetailService;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 해주는 것 
//아래의 3개의 어노테이션은 세트로 생각해도 된다. 모르면 일단 걸고 쓰고 ! 공부하자 마현아

@Configuration // 빈등록 (IoC관리)
@EnableWebSecurity // security라는 필터를 추가 = 스프링 시큐리티가 활성화가 되어있는데 어떤 설정을 해당 파일에서 하겠다. (보충설명) : 시큐리티 필터가 등록이 된다. 그걸 여기서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.1
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	
	@Bean //IoC가 됩니다.
	public BCryptPasswordEncoder encodePWD() {
			return new BCryptPasswordEncoder();
	}
	
	//시큐리티가  대신 로그인을 해주는데 password를 가로채기를 하는데
	//해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 ( 테스트시 걸어두는 게 좋음) //나중에 설정을 하게되면 csrf 토큰을 계속 날리면서 request 요청을 하면될것 같다. xss와 csrf에 대해서 공부해보도록
			.authorizeRequests() //인증요청이 들어왔을때
				.antMatchers("/auth/**","/","/js/**","/css/**","/image/**","/dummy/**") //이 페이지는
				.permitAll() //허락해줘
				.anyRequest() // 저 페이지들이 아닌 요청은
				.authenticated() // 인증해야돼
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")
				// 이렇게 해놓으면 form에서 action으로 전송한 주소로 요청오는 로그인을 가로채서 대신 로그인해준다.
				.defaultSuccessUrl("/"); //정상적으로 로그인이 완료되었을 때 "/" 로 이동 
				//근데 위의 로그인 가로채기 해서 로그인을 성공시키면 userDetails라는 객체를 사용해서 시큐리티 세션에 저장해줘야하기 때문에 userDetails를 포함하고 있는 객체를 만들어줘야한다.
	}
}
