package com.magu.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import com.magu.blog.model.RoleType;
import com.magu.blog.model.User;
import com.magu.blog.repository.UserRepository;

//html파일이 아니라 data를 리턴해주는 controller = > restController
@RestController
public class DummyController {	
								
							  // 의존성 주입 (DI)
		@Autowired // 이걸붙여주면 위의 restController 메모리에 올려줄 때 같이 올려준다.
		private UserRepository userRepository;
	
		
		@GetMapping("/dummy/users")
		public List<User> list(){
			return userRepository.findAll();
		}
		
		// http://localhost:8000/blog/dummy/user 다음 페이지 보려면 ?page=0 , page=1 ~~~~~
		// 한 페이지당 2건의 데이터를 리턴받아 볼 예정, 페이징 처리
		@GetMapping("/dummy/user")
		public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
			List<User> users = userRepository.findAll(pageable).getContent();//findAll(pageable)
			//Page<User> users = userRepository.findAll(pageable);// page의 세부 데이터도 다 볼수 있다.
			
//			Page<User> pagingUser = userRepository.findAll(pageable);//findAll(pageable)
//			if(pagingUser.isLast()) {
//				
//			}
//			List<User> users = pagingUser.getContent();
			
			return users;
		}
		
		//{id} 주소로 파라메터를 전달 받을 수 있음.
		// http://localhost:8000/blog/dummy/user/3
		@GetMapping("/dummy/user/{id}")
		public User detail(@PathVariable int id) {
			
			//현재상황 데이터베이스에 id 3 까지 들어가있음
			
			// user/4를 찾으면 내가 데이터 베이스에서 못찾아오게 되면 user가 null이 될 것 아냐?
			// 그럼 return 할 때 null이 리턴이 되자나 그럼 프로그램에 문제가 있지 않겠니?
			// Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해 !!
			
//			User user = userRepository.findById(id).get(); //음~나는 그럴일 없어 .get() 바로 줌
//			User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//				// 인터페이스는 new 하려면 익명 객체가 필요함 그리고 오버라이딩 해줘야함 
//				@Override
//				public User get() {
//					// TODO Auto-generated method stub
//					return new User(); //빈객체 먹고 꺼져라!
//				}
//			});
			
			User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
				@Override
				public IllegalArgumentException get() {
					// TODO Auto-generated method stub
					return new IllegalArgumentException("해당 유저는 없습니다. id " + id);
				}
			});
			//이런 exception 이 발생하면 whiteerrorpage가 보여짐으로 aop <---공부해보세요
			
// 위의 식을 람다식으로 변경해보자 !!! 람다식 !!
//			User user = userRepository.findById(id).orElseThrow(()->{
//				return new IllegalArgumentException("해당사용자는 없습니다.");
//			});
			
			
			// 요청은 웹 브라우저에서 했음
			//return 에서 user 객체는 = 자바 오브젝트자나 
			//변환 ( 웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
			//스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
			// 만약에 자바 오브젝트를 리턴하게 되면 MessageController가 Jackson 라이브러리를 호출해서
			// user 오브젝트를 json으로 변환해서 던져준다.
			return user;
		}
		
	   // http://localhost:8000/blog/dummy/join  (요청)
		// http의 body에 username, password, email 데이터를 가지고 (요청)
		@PostMapping("/dummy/join2")
		public String Join(String username, String password, String email) { //key = value (약속된 규칙) x-www-form-urlencode
			System.out.println("username: " + username);
			System.out.println("password: " + password);
			System.out.println("email: " + email);
				return "회원가입이 완료되었습니다.";
		}
		
		@PostMapping("/dummy/join")
		public String Join(User user) { 
			System.out.println(user);
			System.out.println("id: " + user.getId());
			System.out.println("role: " + user.getRole());
			System.out.println("createDate: " + user.getCreateDate());
			System.out.println("username: " + user.getUsername());
			System.out.println("password: " + user.getPassword());
			System.out.println("email: " + user.getEmail());
			
			user.setRole(RoleType.USER);
			userRepository.save(user); 
			return "회원가입이 완료되었습니다.";
		}
}
