<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<h2>글쓰기</h2>
	<form>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Enter username" id="title">
		</div>
		<div class="form-group">
			<textarea class="form-control summernote"  rows="5" id="content"></textarea>
		</div>
	</form>
	<button type="button" id = "btn-save"class="btn btn-primary">저장</button>
</div>

<script>
	$('.summernote').summernote({
		placeholder : '글을 작성해주세요',
		tabsize : 2,
		height : 300
	});
</script>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>



