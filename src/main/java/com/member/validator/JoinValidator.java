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
		String emailId = member.getEmailId();
		String pass = member.getPass();
		String conpass = member.getConpass();
		
		
		if(emailId == ""){
			errors.rejectValue("emailId", "", "�̸����� �Է����ּ���.");
		}if(emailId.indexOf("@") > 14){
			errors.rejectValue("emailId", "", "���̸� �ʰ��Ͽ����ϴ�.");
		}
		
		if(member.getName() == ""){
			errors.rejectValue("name", "", "�̸��� �Է����ּ���");
		}if(member.getPass() == ""){
			errors.rejectValue("pass", "", "�н����带 �Է����ּ���");
		}if(!pass.equals(conpass)){
			errors.rejectValue("conpass", "", "��ȣ�� ��ġ���� �ʽ��ϴ�.");
		}
	}


}
