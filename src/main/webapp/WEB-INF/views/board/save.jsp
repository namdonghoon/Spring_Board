<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<spring:hasBindErrors name="commandWriter"/>
<h1>�۾��� ��</h1>
<form action="/board/save" method="post">
<div>
	<label>�ۼ���</label>
	<input value="${accessId}" type="text" disabled>
</div>
<div>
	<label>����</label>
	<input name="title" type="text">
	<form:errors path="commandWriter.title"/>
</div>
<div>
	<label>����</label>
	<textarea name="content" rows="40" cols="60"></textarea>
</div>
<input type="submit" value="�۾���">
</form>
</body>
</html>