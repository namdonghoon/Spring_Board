$(document).ready(function($) {
	
	$("#Email_id").blur(function () {
	  	var email = $(this).val();
	  	if(email != ""){
	  		location.href="/member/idCheck?email="+email;
	  	}
	});

});
