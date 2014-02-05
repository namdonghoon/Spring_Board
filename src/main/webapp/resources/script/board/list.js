function boardinsert() {
	location.href = "saveBoard";
}
function logout(){
	alert("로그아웃 되었습니다.");
	location.href = "logout";
}


$(document).ready(function($) {
	//현제 페이지 색상 변경 
	var currentPage = $("#currentPage").val();
	
	$("#board_page_set #link_page").each(function(n) {
		if($(this).text().trim() == currentPage){
			$(this).css({
				"color": "blue"
			});
		}
	});
	

	
});