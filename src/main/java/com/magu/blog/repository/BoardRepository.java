package com.magu.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magu.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	
}
