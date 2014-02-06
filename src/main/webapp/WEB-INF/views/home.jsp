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
<h3>로그인</h3>
<div id="login_form"> 
	<form action="/member/login" method="post"> 
		<label>이메일 : </label><input name="email" type="email">
		<%-- <form:errors path="commandLogin.email"/> --%>
		<br>
		<label>암  호 : </label><input name="pass" type="password">
		<%-- <form:errors path="commandLogin.pass"/> --%>
		<br>
		<input type="submit" value="로그인">
	</form>
</div>
<hr>

<div id="insert_form">
<spring:hasBindErrors name="commandJoin"/>
<h3>회원 가입폼</h3>
<form action="/member/save" method="post"> 
	<label>이메일 : </label>
		<input id="Email_id" name="email" type="email" value="${email}">
		<%-- <form:errors path="commandJoin.email"/> --%>
	<br>
	<label>이  름 : </label>
	<input name="name" type="text">
		<%-- <form:errors path="commandJoin.name"/> --%>
	<br>
	<label>암  호 : </label>
	<input name="pass" type="password">
		<%-- <form:errors path="commandJoin.pass"/>  --%>
	<br>
	<label>암호확인 : </label>
	<input name="conpass" type="password">
		<%-- <form:errors path="commandJoin.conpass"/> --%>
	<br>
	<input type="submit" value="회원가입">
</form>
</div>



</body>
</html>
