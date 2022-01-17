package com.magu.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
/*
@AllArgsConstructor // 생성자 만들고 싶으면 이렇게 사용 
근데 요즘엔 변수에 final에 붙이고 @RequiredArgsConstructor 이것을 사용하여  final에 붙어있는 것에대한것만 생성
*/
@Data //@getter , @setter 합친것
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	private  int id;
	private  String username;
	private  String password;
	private  String email;
}


