$(document).ready(function( $ ) {
	var session_id = $('input[name=session_id]').val();  //�� ���� ���̵� 
	var board_Email = $('input[name=board_Email]').val(); //��� �Խñ� ���� 
	var manage_id = $('input[name=manager_id]').val(); //������ ���̵�  
	
	//��ũ��Ʈ ������ ���� �ο�
	if(session_id == manage_id){
		session_id==board_Email;
	}
	
	//������ ID�� �ش� �Խ���, ������ID�� �ƴ� ��� ���. 
	if(session_id != board_Email && session_id != manage_id){
		$('input[name=board_title]').attr('disabled', true);
		$('textarea[name=board_content]').attr('disabled', true);
	}
	
	$("#bt_update").click(function(){
		if(session_id != board_Email  && session_id != manage_id){
			alert("���ٱ����� �����ϴ�.");
		}else{
			alert("���� �����Ǿ����ϴ�.");
		}
	});
	
	$("#bt_delete").click(function(){
		if(session_id != board_Email  && session_id != manage_id){
			alert("���ٱ����� �����ϴ�.");
		}else{
			alert("���� �����Ǿ����ϴ�.");
		}
	 });
});