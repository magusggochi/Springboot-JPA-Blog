<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/auth/loginProc"  method="post">
		<div class="form-group">
			<label for="username">UserName:</label> <input type="text" name = "username" class="form-control" placeholder="Enter username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password:</label> <input type="password" name = "password" class="form-control" placeholder="Enter password" id="password">
		</div>

		<div class="form-group form-check">
			<label class="form-check-label"> <input class="form-check-input"  name = "remember" type="checkbox"> Remember Me
			</label>
		</div>
	<button type="submit" class="btn btn-primary">로그인</button>
	<a href="https://kauth.kakao.com/oauth/authorize?client_id=33b2b99e5c94c712bed97a7f5c718e0d&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38" src="/image/kakao_login_button.png"/></a>
	</form>
</div>

<%@ include file="../layout/footer.jsp"%>

