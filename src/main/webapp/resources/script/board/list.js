function boardinsert() {
	location.href = "/board/save";
}
function logout(){
	alert("�α׾ƿ� �Ǿ����ϴ�.");
	location.href = "/member/logout";
}


$(document).ready(function($) {
	//���� ������ ���� ���� 
	var currentPage = $("#currentPage").val();
	
	$("#board_page_set #link_page").each(function(n) {
		if($(this).text().trim() == currentPage){
			$(this).css({
				"color": "blue"
			});
		}
	});
	

	
});