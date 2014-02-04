<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>   
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="http://code.jquery.com/jquery-1.6.3.min.js"></script>
<script src="/resources/script/board_home.js" type="text/javascript"></script>

<title>Home</title>
</head>
<body>



<spring:hasBindErrors name="command_login"/>
<h3>�α���</h3>
<div id="login_form">
	<form action="login" method="post"> 
		<label>�̸��� : </label><input name="member_Email" type="email">
		<form:errors path="command_login.member_Email"/>
		<br>
		<label>��  ȣ : </label><input name="member_pass" type="password">
		<form:errors path="command_login.member_pass"/>
		<br>
		<input type="submit" value="�α���">
	</form>
</div>
<hr>

<div id="insert_form">
<spring:hasBindErrors name="command_join"/>
<h3>ȸ�� ������</h3>
<form action="member_insert" method="post"> 
	<label>�̸��� : </label>
		<input id="Email_id" name="member_Email" type="email" value="${member_Email}">
		<form:errors path="command_join.member_Email"/>
	<br>
	<label>��  �� : </label>
	<input name="member_name" type="text">
		<form:errors path="command_join.member_name"/>
	<br>
	<label>��  ȣ : </label>
	<input name="member_pass" type="password">
		<form:errors path="command_join.member_pass"/> 
	<br>
	<label>��ȣȮ�� : </label>
	<input name="member_conpass" type="password">
		<form:errors path="command_join.member_conpass"/>
	<br>
	<input type="submit" value="ȸ������">
</form>
</div>



</body>
</html>
