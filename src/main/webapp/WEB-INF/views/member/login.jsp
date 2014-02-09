<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="http://code.jquery.com/jquery-1.6.3.min.js"></script>
<script src="../resources/script/member/login.js" type="text/javascript"></script>
<Link href="../resources/css/member/login.css" rel="stylesheet" type="text/css"/>
<title>login</title>
</head>
<body>

<div id="login_form">
	<div id="errer_message">
	<input id="email_errer" type="hidden" value="${emailErrer}">
	<input id="pass_errer" type="hidden" value="${passErrer}">
	</div> 
	<form:form action="/member/loginRe" method="POST" modelAttribute="login" autocomplete="off">
		<input name="name" type="hidden" value="empty">
		<label>이메일 : </label><input name="email" type="email" value="${email}">
		<form:errors path="email" cssClass="errors"/>
		<br>
		<label>암  호 : </label><input name="pass" type="password">
		<form:errors path="pass" cssClass="errors" />
		<br>
		<input type="submit" value="로그인">
		<input type="button" onclick="location.href='/member/join'" value="회원가입">
	</form:form>
</div>

</body>
</html>