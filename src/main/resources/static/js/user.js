/* User 동작 스크립트 */

let index = {

		init: function(){
			$('#btn-user-save').on("click", ()=>{  //fucntion(){}, 대신 ()=> 쓰는 이유는 this를 바인딩하기 위해서!!! ( 이 함수 내부의 this 값이랑 이 외부 index의 this를 다르게 쓰기 위해서 function을 사용하면 this 가 외부 내부 같음)
				this.save();
			});
			$('#btn-user-update').on("click", ()=>{  //fucntion(){}, 대신 ()=> 쓰는 이유는 this를 바인딩하기 위해서!!! ( 이 함수 내부의 this 값이랑 이 외부 index의 this를 다르게 쓰기 위해서 function을 사용하면 this 가 외부 내부 같음)
				this.update();
			});
		},
		
		save: function(){
			// alert("user의 save함수 호출됨");
			let data = {
					username : $('#username').val(),
					password : $('#password').val(),
					email : $('#email').val()
			};
			//console.log(data);
			
			//ajax 호출시 default가 비동기 호출
			// ajax 통신을 이용해서 3개의 데이터를  json으로 변경하여 insert요청!!
			// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해주네요?? 
			$.ajax({
				//회원가입 수행요청
				type: "POST",
				url: "/auth/joinProc",
				data: JSON.stringify(data), //http body데이터
				contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (MIME)
				dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열(String) 근데 생긴게 json이면 = > javascript 오브젝트로 변경
			}).done(function(response){
				if(response.status === 500){
					alert("회원가입에 실패했습니다.");
				}else{
					console.log(response);
					alert("회원가입이 완료되었습니다.");
					location.href="/";
				}
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		},
		
		update: function(){
			let data = {
					id  :$('#id').val(),
					username : $('#username').val(),
					password : $('#password').val(),
					email : $('#email').val()
			};
			$.ajax({
				type: "PUT",
				url: "/auth/updateProc",
				data: JSON.stringify(data), //http body데이터
				contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (MIME)
				dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열(String) 근데 생긴게 json이면 = > javascript 오브젝트로 변경
			}).done(function(response){
				console.log(response);
				alert("회원수정이 완료되었습니다.");
				location.href="/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		}
}

index.init();