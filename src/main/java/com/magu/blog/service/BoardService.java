package com.magu.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magu.blog.model.Board;
import com.magu.blog.model.User;
import com.magu.blog.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public void boardSave(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public List<Board> selectBoardList() {
		return boardRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Page<Board> selectBoardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board selectBoardDetail(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패");
		});
	}

	@Transactional
	public void deleteBoard(int id) {
		boardRepository.deleteById(id);
	}

	@Transactional 
	public void updateBoard(int id, Board requestBoard) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패");
		}); //영속화 완료 --> 영속화 한 값이 달라지는 경우 더티 체킹으로 업데이트 하려고 한다.
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수 종료시 (Service가 종료될 때) 트랜잭션이 종료됩니다. 이 때 더티체킹이 일어난다. - 자동 업데이트가 db flush
		
	}
}
