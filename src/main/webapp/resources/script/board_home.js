$(document).ready(function($) {
	
	$("#Email_id").blur(function () {
	  	var Email_id = $(this).val();
	  	if(Email_id != ""){
	  		location.href="memberIdCheck?emailId="+Email_id;
	  	}
	});

});
