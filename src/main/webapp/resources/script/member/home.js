$(document).ready(function($) {
	
	if($("#email_errer").val() == "true" || $("#email_errer").val() != ""){
		var html ="<span id='email_errer_info'>�̹� ���Ե� �̸����Դϴ�.</span>";
		$("#email_input").after(html);
	}
//	if($("#pass_errer").val() == "true" || $("#pass_errer").val() != ""){
//		var html ="<span id='pass_errer_info'>�н����尡 ��ġ���� �ʽ��ϴ�.</span>";
//		$("#pass_input").after(html);
//	}
	
	$("#email_input").blur(function () {
	  	var email = $(this).val();
	  	
	  	if(email != ""){ 
	  		location.href="/member/emailCheck?email="+email;
	  	}
	});

	
});

