<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="http://code.jquery.com/jquery-1.6.3.min.js"></script>
<script src="/resources/script/board_check.js" type="text/javascript"></script>
<link rel="stylesheet" href="/resources/css/board_check.css" type="text/css"/>
<title>Insert title here</title>
</head> 
<body>
<h3>선택한 글</h3>
<form action="board_update" method="post">
<input value="${board.board_num}" name="board_num" type="hidden">
<input value="${Email_id}" name="session_id" type="hidden">
<input value="${manager_id}" name="manager_id" type="hidden">
<input value="${board.board_Email}" name="board_Email" type="hidden">

<div>
	<label>작성자</label>
	<input value="${board.board_Email}" type="text" disabled>
</div>
<div>
	<label>제목</label>
	<input  value="${board.board_title}" name="board_title" type="text">
</div>
<div>
	<label>내용</label>
	<textarea name="board_content" rows="20" cols="60">${board.board_content}</textarea>
</div>
	<input id="bt_update" type="submit" value="수정"> 
</form>

<form action="board_delete" method="post">
	<input value="${board.board_Email}" name="board_Email" type="hidden">
	<input value="${board.board_num}" name="board_num" type="hidden">
	<input id="bt_delete" type="submit" value="삭제">

</form>
</body>
</html>