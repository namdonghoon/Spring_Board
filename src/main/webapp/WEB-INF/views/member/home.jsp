<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>   
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="http://code.jquery.com/jquery-1.6.3.min.js"></script>
<script src="../resources/script/member/home.js" type="text/javascript"></script>
<Link href="../resources/css/member/home.css" rel="stylesheet" type="text/css"/>
<title>Home</title>
</head>
<body>

<h3>로그인</h3>
<div id="login_form"> 
	<form:form action="/member/login" method="POST" modelAttribute="login" autocomplete="off">
		<input name="name" type="hidden" value="empty">
		<label>이메일 : </label>
		<input name="email" type="email">
		<form:errors path="email" cssClass="errors"/>
		<br>
		<label>암  호 : </label>
		<input name="pass" type="password">
		<form:errors path="pass" cssClass="errors"/>
		<br>
		<input type="submit" value="로그인">
	</form:form>
</div>
<hr>

<div id="insert_form">
<h3>회원 가입폼</h3>
<form:form action="/member/save" method="POST" modelAttribute="save" autocomplete="off">
	<input id="email_errer" type="hidden" value="${emailErrer}">
	<input id="pass_errer" type="hidden" value="${passErrer}">
	
	<label>이메일 : </label>
		<input id="email_input" name="email" type="email" value="${email}">
		<form:errors path="email" cssClass="errors"/>
	<br>
	<label>이  름 : </label>
	<input name="name" type="text">
		<form:errors path="name" cssClass="errors"/>
	<br>
	<label>암  호 : </label>
		<input id="pass_input" name="pass" type="password">
		<form:errors path="pass" cssClass="errors"/>
	<br>
	<label>암호확인 : </label>
	<input name="conpass" type="password">
	 	<form:errors  path="conpass" cssClass="errors"/>
	<br>
	<input type="submit" value="회원가입">
</form:form>
</div>



</body>
</html>
