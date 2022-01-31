/* User 동작 스크립트 */

let index = {

		init: function(){
			$('#btn-save').on("click", ()=>{  //fucntion(){}, 대신 ()=> 쓰는 이유는 this를 바인딩하기 위해서!!! ( 이 함수 내부의 this 값이랑 이 외부 index의 this를 다르게 쓰기 위해서 function을 사용하면 this 가 외부 내부 같음)
				this.save();
			});
			
			$('#btn-delete').on("click", ()=>{  //fucntion(){}, 대신 ()=> 쓰는 이유는 this를 바인딩하기 위해서!!! ( 이 함수 내부의 this 값이랑 이 외부 index의 this를 다르게 쓰기 위해서 function을 사용하면 this 가 외부 내부 같음)
				this.deleteById();
			});
			
			$('#btn-update').on("click", ()=>{  //fucntion(){}, 대신 ()=> 쓰는 이유는 this를 바인딩하기 위해서!!! ( 이 함수 내부의 this 값이랑 이 외부 index의 this를 다르게 쓰기 위해서 function을 사용하면 this 가 외부 내부 같음)
				this.update();
			});
			
			$('#btn-reply-save').on("click", ()=>{  
				this.replySave();
			});
			
		},
		
		save: function(){
			let data = {
					content : $('#content').val(),
					title : $('#title').val()
			};
			
			$.ajax({
				type: "POST",
				url: "/api/board",
				data: JSON.stringify(data), //http body데이터
				contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (MIME)
				dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열(String) 근데 생긴게 json이면 = > javascript 오브젝트로 변경
			}).done(function(response){
				console.log(response);
				alert("작성을 완료했습니다.");
				location.href="/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		},
		deleteById: function(){
			let id = $('#id').text();
			$.ajax({
				type: "DELETE",
				url: "/api/board/"+id,
				dataType:"json"
			}).done(function(response){
				console.log(response);
				alert("삭제가 완료되었습니다.");
				location.href="/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		},
		
		update: function(){
			let id = $("#boardId").val();
			
			let data = {
					content : $('#content').val(),
					title : $('#title').val()
			};
			
			$.ajax({
				type: "PUT",
				url: "/api/board/"+id,
				data: JSON.stringify(data), //http body데이터
				contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (MIME)
				dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열(String) 근데 생긴게 json이면 = > javascript 오브젝트로 변경
			}).done(function(response){
				console.log(response);
				alert("글 수정이 완료되었습니다.");
				location.href="/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		},
		
		replySave: function(){
			let data = {
					content : $('#reply-content').val()
			};
			let boardId = $('#boardId').val();
			
			//console.log(data);
			$.ajax({
				type: "POST",
				url: `/api/board/${boardId}/reply`,
				data: JSON.stringify(data), //http body데이터
				contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (MIME)
				dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열(String) 근데 생긴게 json이면 = > javascript 오브젝트로 변경
			}).done(function(response){
				console.log(response);
				alert("리플 작성을 완료했습니다.");
				location.href=`/board/${boardId}`;
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		},
		
		replyDelete: function(boardId, replyId){
			$.ajax({
				type: "DELETE",
				url: `/api/board/${boardId}/reply/${replyId}`,
				dataType:"json"
			}).done(function(response){
				console.log(response);
				alert("댓글삭제 성공");
				location.href=`/board/${boardId}`;
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		}
		
}

index.init();