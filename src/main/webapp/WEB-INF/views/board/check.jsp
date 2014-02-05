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
<link rel="stylesheet" href="../resources/css/board/check.css" type="text/css"/>
<title>Insert title here</title>
</head> 
<body>
<h3>������ ��</h3>
<form action="updateBoard" method="post">
<input value="${board.numberId}" name="numberId" type="hidden">
<input value="${accessId}" name="accessId" type="hidden">
<input value="${managerId}" name="managerId" type="hidden">
<input value="${board.email}" name="email" type="hidden">

<div>
	<label>�ۼ���</label>
	<input value="${board.email}" type="text" disabled>
</div>
<div>
	<label>����</label>
	<input  value="${board.title}" name="title" type="text">
</div>
<div>
	<label>����</label>
	<textarea name="content" rows="20" cols="60">${board.content}</textarea>
</div>
	<input id="bt_update" type="submit" value="����"> 
</form>

<form action="deleteBoard" method="post">
	<input value="${board.email}" name="email" type="hidden">
	<input value="${board.numberId}" name="numberId" type="hidden">
	<input id="bt_delete" type="submit" value="����">

</form>
</body>
</html>