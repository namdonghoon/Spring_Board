<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>   
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="http://code.jquery.com/jquery-1.6.3.min.js"></script>
<script src="/resources/script/home.js" type="text/javascript"></script>

<title>Home</title>
</head>
<body>



<%-- <spring:hasBindErrors name="commandLogin"/> --%>
<h3>�α���</h3>
<div id="login_form"> 
	<form action="/member/login" method="post"> 
		<label>�̸��� : </label><input name="email" type="email">
		<%-- <form:errors path="commandLogin.email"/> --%>
		<br>
		<label>��  ȣ : </label><input name="pass" type="password">
		<%-- <form:errors path="commandLogin.pass"/> --%>
		<br>
		<input type="submit" value="�α���">
	</form>
</div>
<hr>

<div id="insert_form">
<spring:hasBindErrors name="commandJoin"/>
<h3>ȸ�� ������</h3>
<form action="/member/save" method="post"> 
	<label>�̸��� : </label>
		<input id="Email_id" name="email" type="email" value="${email}">
		<%-- <form:errors path="commandJoin.email"/> --%>
	<br>
	<label>��  �� : </label>
	<input name="name" type="text">
		<%-- <form:errors path="commandJoin.name"/> --%>
	<br>
	<label>��  ȣ : </label>
	<input name="pass" type="password">
		<%-- <form:errors path="commandJoin.pass"/>  --%>
	<br>
	<label>��ȣȮ�� : </label>
	<input name="conpass" type="password">
		<%-- <form:errors path="commandJoin.conpass"/> --%>
	<br>
	<input type="submit" value="ȸ������">
</form>
</div>



</body>
</html>
