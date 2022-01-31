package com.magu.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.magu.blog.model.Board;
import com.magu.blog.repository.BoardRepository;

@RestController
public class ReplyControllerCheck {

	@Autowired
	private BoardRepository boardRepository;
	
	//무한 참조에 대한 테스트
	@GetMapping("/test/board/{id}")
	public Board getBoard(@PathVariable int id) {
		return boardRepository.findById(id).get(); //Jakson 라이브러리 ( 오브젝트를 json으로 리턴 ) => 모델의 getter를 호출
	}
}
