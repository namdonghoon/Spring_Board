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

<h3>�α���</h3>
<div id="login_form"> 
	<form:form action="/member/login" method="POST" modelAttribute="login" autocomplete="off">
		<input name="name" type="hidden" value="empty">
		<label>�̸��� : </label>
		<input name="email" type="email">
		<form:errors path="email" cssClass="errors"/>
		<br>
		<label>��  ȣ : </label>
		<input name="pass" type="password">
		<form:errors path="pass" cssClass="errors"/>
		<br>
		<input type="submit" value="�α���">
	</form:form>
</div>
<hr>

<div id="insert_form">
<h3>ȸ�� ������</h3>
<form:form action="/member/save" method="POST" modelAttribute="save" autocomplete="off">
	<input id="email_errer" type="hidden" value="${emailErrer}">
	<input id="pass_errer" type="hidden" value="${passErrer}">
	
	<label>�̸��� : </label>
		<input id="email_input" name="email" type="email" value="${email}">
		<form:errors path="email" cssClass="errors"/>
	<br>
	<label>��  �� : </label>
	<input name="name" type="text">
		<form:errors path="name" cssClass="errors"/>
	<br>
	<label>��  ȣ : </label>
		<input id="pass_input" name="pass" type="password">
		<form:errors path="pass" cssClass="errors"/>
	<br>
	<label>��ȣȮ�� : </label>
	<input name="conpass" type="password">
	 	<form:errors  path="conpass" cssClass="errors"/>
	<br>
	<input type="submit" value="ȸ������">
</form:form>
</div>



</body>
</html>
