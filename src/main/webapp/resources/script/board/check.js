$(document).ready(function( $ ) {
	var email = $('input[name=email]').val(); //��� �Խñ� ���� 
	var accessId = $('input[name=accessId]').val();  //�� ���� ���̵� 
	var manageId = $('input[name=managerId]').val(); //������ ���̵�  
	
	//��ũ��Ʈ ������ ���� �ο�
	if(accessId == manageId){
		accessId == email;
	}
	
	//������ ID�� �ش� �Խ���, ������ID�� �ƴ� ��� ���. 
	if(accessId != email && accessId != manageId){
		$('input[name=title]').attr('disabled', true);
		$('textarea[name=content]').attr('disabled', true);
	}
	
	$("#bt_update").click(function(){
		if(accessId != email  && accessId != manageId){
			alert("���ٱ����� �����ϴ�.");
		}else{
			alert("���� �����Ǿ����ϴ�.");
		}
	});
	
	$("#bt_delete").click(function(){
		if(accessId != email  && accessId != manageId){
			alert("���ٱ����� �����ϴ�.");
		}else{
			alert("���� �����Ǿ����ϴ�.");
		}
	 });
});