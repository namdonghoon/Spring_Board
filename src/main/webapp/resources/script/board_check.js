$(document).ready(function( $ ) {
	var session_id = $('input[name=session_id]').val();  //내 접속 아이디 
	var board_Email = $('input[name=board_Email]').val(); //상대 게시글 아이 
	var manage_id = $('input[name=manager_id]').val(); //관리자 아이디  
	
	//스크립트 관리자 권한 부여
	if(session_id == manage_id){
		session_id==board_Email;
	}
	
	//접속한 ID와 해당 게시자, 관리자ID가 아닐 경우 잠금. 
	if(session_id != board_Email && session_id != manage_id){
		$('input[name=board_title]').attr('disabled', true);
		$('textarea[name=board_content]').attr('disabled', true);
	}
	
	$("#bt_update").click(function(){
		if(session_id != board_Email  && session_id != manage_id){
			alert("접근권한이 없습니다.");
		}else{
			alert("글이 수정되었습니다.");
		}
	});
	
	$("#bt_delete").click(function(){
		if(session_id != board_Email  && session_id != manage_id){
			alert("접근권한이 없습니다.");
		}else{
			alert("글이 삭제되었습니다.");
		}
	 });
});