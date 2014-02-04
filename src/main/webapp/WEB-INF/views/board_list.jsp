<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="http://code.jquery.com/jquery-1.6.3.min.js"></script>
<script src="/resources/script/board_list.js" type="text/javascript"></script>
<link rel="stylesheet" href="/resources/css/board_list.css" type="text/css"/>
<title>Insert title here</title>
</head>

<body>
<div id="home">
	<a href="home">Home</a>
</div>
<div id="access_group">
	<label>접속아이디: </label> 
	<label id="access_id">${Email_id}</label>
	<input onclick="logout()" type="button" value="로그아웃">
</div>
<hr>
<h3>글 목록입니다.</h3>
<div id="board_list_table_size"> 
<table id="board_list" >
	<tr>
		<th colspan="2" id="title">글제목</th>
		<th id="writer">작성자</th>
		<th id="date">작성일자</th>
	</tr>
	<c:forEach var="list" items="${list}" >
	<tr>
		<td>${list.board_num}</td>
		<td><a href="board_check?board_num=${list.board_num}">${list.board_title}</a></td>
		<td>${list.board_Email}</td>
		<td>
			<fmt:formatDate value="${list.board_date}" pattern="MM.dd. hh:mm"/>
		</td>
	</tr>
	</c:forEach>
</table>
<input id="bt_writing" onclick="boardinsert()" type="button" value="글쓰기">
</div>
<div id="board_page_set">
<input id="currentPage" value="${pageNum+1}" type="hidden" >
<input id="startPage" value="${startPage}" type="hidden" >
<input id="endPage" value="${endPage}" type="hidden" >

<c:if test="${startPage >= pgMaxCount}"><a id="prev" href="board_list?pageNum=${startPage-pgMaxCount}">이전</a></c:if>
<c:forEach var="pageNum" begin="${startPage}" end="${endPage}">
	<label for="link_page">
		<a id="link_page" href="board_list?pageNum=${pageNum}">
			${pageNum}
		</a>
	</label>
</c:forEach>
<c:if test="${startPage + (pgMaxCount-1) <= pgTotalCount}"><a id="next" href="board_list?pageNum=${startPage+pgMaxCount}">다음</a></c:if>
</div>


</body>
</html>