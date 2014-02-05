<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="http://code.jquery.com/jquery-1.6.3.min.js"></script>
<script src="../resources/script/board/list.js" type="text/javascript"></script>
<link rel="stylesheet" href="../resources/css/board/list.css" type="text/css"/>
<title>Insert title here</title>
</head>
 
<body>
<div id="home"> 
	<a href="home">Home</a>
</div>
<div id="access_group">
	<label>���Ӿ��̵�: </label> 
	<label id="access_id">${accessId}</label>
	<input onclick="logout()" type="button" value="�α׾ƿ�">
</div>
<hr>
<h3>�� ����Դϴ�.</h3>
<div id="board_list_table_size"> 
<table id="board_list" >
	<tr>
		<th colspan="2" id="title">������</th>
		<th id="writer">�ۼ���</th>
		<th id="date">�ۼ�����</th>
	</tr>
	<c:forEach var="list" items="${list}" >
	<tr>
		<td>${list.numberId}</td>
		<td><a href="checkBoard?numberId=${list.numberId}">${list.title}</a></td>
		<td>${list.email}</td>
		<td>
			<fmt:formatDate value="${list.date}" pattern="MM.dd. hh:mm"/>
		</td> 
	</tr>
	</c:forEach>
</table>
<input id="bt_writing" onclick="boardinsert()" type="button" value="�۾���">
</div>
<div id="board_page_set">
<input id="currentPage" value="${pageNum+1}" type="hidden" >
<input id="startPage" value="${startPage}" type="hidden" >
<input id="endPage" value="${endPage}" type="hidden" >

<c:if test="${startPage >= pageMaxCount}"><a id="prev" href="boardList?pageNum=${startPage-pageMaxCount}">����</a></c:if>
<c:forEach var="pageNum" begin="${startPage}" end="${endPage}">
	<label for="link_page">
		<a id="link_page" href="boardList?pageNum=${pageNum}">
			${pageNum}
		</a>
	</label>
</c:forEach>
<c:if test="${startPage + (pageMaxCount-1) <= pageTotalCount}"><a id="next" href="boardList?pageNum=${startPage+pageMaxCount}">����</a></c:if>
</div>


</body>
</html>