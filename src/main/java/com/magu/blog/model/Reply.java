package com.magu.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {
	
	@Id // primary key 
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. AND use-new-id-generator-mappings: falseuse-new-id-generator-mappings: false 라고 YML 에 표현되어있는데 FALSE로 해놓으면 JPA 전략 넘버링을 따라가지 않는다는 뜻
	private int id; // 시퀀스, mysql에서는 auto_increment 하도록 명시
	
	@Column(nullable = false, length = 200)
	private String content; 
	
	@ManyToOne //여러개의 답변은 하나의 게시글에 있을 수 있어
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne  // 접미에 오는 many or one은 최상위 객체의 상태로 생각하면 됨, 접두는 밑에 생성한 객체로 
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
}
