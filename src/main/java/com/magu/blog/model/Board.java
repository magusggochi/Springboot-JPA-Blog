package com.magu.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!!
@Entity
public class Board {
		
		@Id //primary key
		@GeneratedValue(strategy = GenerationType.IDENTITY)// auto_increment 사용
		private int id;
		
		@Column(nullable = false, length = 100)
		private String title;
		
		@ Lob // 대용량 데이터 일때 사용
		private String content; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인이 됨. 
		
		@ColumnDefault("0") // int니까 전 user에서 문자에 '' 달았던것과 달리 그냥 씀
		private int count; // 조회수
		
		@ManyToOne // Many  = Board , One = User 한명의 유저는 여러개의 게시글을 쓸 수 있다.
		@JoinColumn(name="userId")
		private User user; //DB는 오브젝트를 저장할 수 없다. FK(Foreign Key 외래키 ), 자바는 오브젝트를 저장할 수 있다.
										 //생성은 객체로 했는데 실제  DB로 가면 INT로 FK로 만들어 졌다.
		
										 //ORM을 사용하면 객체 자체를 사용할 수 있음 그대신 어노테이션 @JoinCloumn 으로 표현해야함.
		
																	  // mappedBy 하고 = "이름"  여기에 들어가는 이름은 Reply의 Board 객체 생성 이름을 적는다.
		@OneToMany(mappedBy = "board") // mappedBy 연관관계의 주인이 아니다. ( 난 FK 가 아니에요 ) DB에 컬럼을 만들지 마세요 나는 그냥 데이터를 원하는 것 뿐이에요
		//@JoinColumn(name="replyId") 이걸 만약 적으면 ( onenote 연관관계 주인 글 확인 )
		private List<Reply> reply;
		@CreationTimestamp
		private Timestamp createDate;
}
