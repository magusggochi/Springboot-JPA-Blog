package com.magu.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.magu.blog.model.Board;
import com.magu.blog.service.BoardService;

@Controller
public class BoardCotnroller {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({"''","/"})
	public  String index(Model model, @PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable) { 
		//@ AuthenticationPrincipal PrincipalDetail principal //컨트롤러에서 세션을 어떻게 찾는지?
		//System.out.println("로그인 사용자 아이디" + principal.getUsername());
		//WEB-INF/views/index.jsp
		model.addAttribute("boards",boardService.selectBoardList(pageable));
		
		return "index";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/board/{id}")
	public String selectBoardDetail(@PathVariable int id, Model model) {
		
		model.addAttribute("board", boardService.selectBoardDetail(id) );
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.selectBoardDetail(id));
		
		return "board/updateForm";
	}
	
}
