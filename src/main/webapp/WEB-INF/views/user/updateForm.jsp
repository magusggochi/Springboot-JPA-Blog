<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<input type="hidden" value="${principal.user.id}" id="id">
		<div class="form-group">
			<label for="username">UserName:</label> <input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>

		<c:if test="${empty principal.user.oauth}">
			<div class="form-group">
				<label for="password">Password:</label> <input type="password" class="form-control" placeholder="New password" id="password">
			</div>
		</c:if>
		<div class="form-group">
			<label for="email">Email address:</label>
			<c:if test="${empty principal.user.oauth}">
				<input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email">
			</c:if>
			<c:if test="${not empty principal.user.oauth}">
				<input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email" readonly>
			</c:if>
		</div>

	</form>
	<button type="button" id="btn-user-update" class="btn btn-primary">회원정보수정</button>
</div>

<script type="application/javascript" src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>

