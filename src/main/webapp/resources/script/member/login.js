/**
 * session.getAttribute()
 */


$(document).ready(function($) {
	var html="";
	if($("#email_errer").val() == "true"){
		html ="<span>���̵� �������� �ʽ��ϴ�.</span>";
		$("#errer_message").append(html).css('padding', '20');
	}
	
	if($("#pass_errer").val() == "true"){
		html ="<span>��й�ȣ�� Ʋ�Ƚ��ϴ�.</span>";
		$("#errer_message").append(html).css('padding', '20');
	}
	
	
});