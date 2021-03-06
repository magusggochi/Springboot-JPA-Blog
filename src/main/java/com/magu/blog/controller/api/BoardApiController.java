package com.magu.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magu.blog.config.auth.PrincipalDetail;
import com.magu.blog.dto.ResponseDto;
import com.magu.blog.model.Board;
import com.magu.blog.model.Reply;
import com.magu.blog.repository.ReplytRepository;
import com.magu.blog.service.BoardService;

@RestController // 데이터만 전달해줄꺼기 때문에
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<?> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail princiapl) {
		boardService.boardSave(board,princiapl.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<?> deleteById(@PathVariable int id) {
		boardService.deleteBoard(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> updateBoard(@PathVariable int id, @RequestBody Board board){
		boardService.updateBoard(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	
	//데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
	// dto 사용하지 않는 이유는 
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<?> replySave(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail princiapl) {
		
		boardService.replySave(reply, boardId , princiapl.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
		
		boardService.replyDelete(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
}

