package com.magu.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.magu.blog.model.Reply;

public interface ReplytRepository extends JpaRepository<Reply, Integer>{

	@Query(value="INSERT INTO reply(content, boardId, userId, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery =  true)
	int mSave(Reply reply);
}
