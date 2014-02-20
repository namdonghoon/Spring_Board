<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="http://code.jquery.com/jquery-1.6.3.min.js"></script>
<script src="../resources/script/board/check.js" type="text/javascript"></script>
<link href="../resources/css/board/check.css" rel="stylesheet" type="text/css"/>
<title>Insert title here</title>
</head> 
<body>
<h3>선택한 글</h3>
<form action="/board/update" method="post">
<input value="${board.id}" name="id" type="hidden">
<input value="${accessId}" name="accessId" type="hidden">
<input value="${managerId}" name="managerId" type="hidden">
<input value="${board.email}" name="email" type="hidden">

<div>
	<label>작성자</label>
	<input value="${board.email}" type="text" disabled>
</div>
<div>
	<label>제목</label>
	<input  value="${board.title}" name="title" type="text">
</div>
<div>
	<label>내용</label>
	<textarea name="content" rows="20" cols="60">${board.content}</textarea>
</div>
	<input id="bt_update" type="submit" value="수정"> 
</form>

<form action="/board/delete" method="post">
	<input value="${board.email}" name="email" type="hidden">
	<input value="${board.id}" name="id" type="hidden">
	<input id="bt_delete" type="submit" value="삭제">
	<input id="bt_list" type="button" value="글목록" onclick="location.href ='/board/listBack'">

</form>
</body>
</html>