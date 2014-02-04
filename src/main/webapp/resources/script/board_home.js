$(document).ready(function($) {
	
	$("#Email_id").blur(function () {
	  	var Email_id = $(this).val();
	  	if(Email_id != ""){
	  		location.href="id_check?member_Email="+Email_id;
	  	}
	});

});
