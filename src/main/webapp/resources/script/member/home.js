$(document).ready(function($) {
	
	if($("#email_errer").val() == "true" || $("#email_errer").val() != ""){
		var html ="<span id='email_errer_info'>이미 가입된 이메일입니다.</span>";
		$("#email_input").after(html);
	}
//	if($("#pass_errer").val() == "true" || $("#pass_errer").val() != ""){
//		var html ="<span id='pass_errer_info'>패스워드가 일치하지 않습니다.</span>";
//		$("#pass_input").after(html);
//	}
	
	$("#email_input").blur(function () {
	  	var email = $(this).val();
	  	
	  	if(email != ""){ 
	  		location.href="/member/emailCheck?email="+email;
	  	}
	});

	
});

