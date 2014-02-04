package com.member.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.member.domain.Member;



public class JoinValidator implements org.springframework.validation.Validator{

	//Validator�� �ش� Ŭ������ ���� �� ������ �����ϴ����� ���θ� �����Ѵ�.
	@Override
	public boolean supports(Class clazz) {
        if (Member.class.isAssignableFrom(clazz))
            return true;
        return false;
	}

	/*
	 * target ��ü�� ���� ������ �����Ѵ�. ���� ��� ������ ���� ��� 
	 * errors ��ü�� � ���������� ���� ������ �����Ѵ�.
	*/
	@Override
	public void validate(Object command, Errors errors) {
		Member member = (Member) command;
		String Email_id = member.getMember_Email();
		String Email_pass = member.getMember_pass();
		String Email_conpass = member.getMember_conpass();
		
		
		if(Email_id == ""){
			errors.rejectValue("member_Email", "", "�̸����� �Է����ּ���.");
		}if(Email_id.indexOf("@") > 14){
			errors.rejectValue("member_Email", "", "���̸� �ʰ��Ͽ����ϴ�.");
		}
		
		if(member.getMember_name() == ""){
			errors.rejectValue("member_name", "", "�̸��� �Է����ּ���");
		}if(member.getMember_pass() == ""){
			errors.rejectValue("member_pass", "", "�н����带 �Է����ּ���");
		}if(!Email_pass.equals(Email_conpass)){
			errors.rejectValue("member_conpass", "", "��ȣ�� ��ġ���� �ʽ��ϴ�.");
		}
	}


}
