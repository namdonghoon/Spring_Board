/**
 * session.getAttribute()
 */


$(document).ready(function($) {
	var html="";
	if($("#email_errer").val() == "true"){
		html ="<span>아이디가 존재하지 않습니다.</span>";
		$("#errer_message").append(html).css('padding', '20');
	}
	
	if($("#pass_errer").val() == "true"){
		html ="<span>비밀번호가 틀렸습니다.</span>";
		$("#errer_message").append(html).css('padding', '20');
	}
	
	
});