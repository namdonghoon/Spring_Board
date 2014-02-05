$(document).ready(function( $ ) {
	var email = $('input[name=email]').val(); //상대 게시글 아이 
	var accessId = $('input[name=accessId]').val();  //내 접속 아이디 
	var manageId = $('input[name=managerId]').val(); //관리자 아이디  
	
	//스크립트 관리자 권한 부여
	if(accessId == manageId){
		accessId == email;
	}
	
	//접속한 ID와 해당 게시자, 관리자ID가 아닐 경우 잠금. 
	if(accessId != email && accessId != manageId){
		$('input[name=title]').attr('disabled', true);
		$('textarea[name=content]').attr('disabled', true);
	}
	
	$("#bt_update").click(function(){
		if(accessId != email  && accessId != manageId){
			alert("접근권한이 없습니다.");
		}else{
			alert("글이 수정되었습니다.");
		}
	});
	
	$("#bt_delete").click(function(){
		if(accessId != email  && accessId != manageId){
			alert("접근권한이 없습니다.");
		}else{
			alert("글이 삭제되었습니다.");
		}
	 });
});