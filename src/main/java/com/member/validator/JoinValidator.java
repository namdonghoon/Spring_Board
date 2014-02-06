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
		String email = member.getEmail();
		String pass = member.getPass();
		String conpass = member.getConpass();
		
		
		if(email == ""){
			errors.rejectValue("email", "", "�̸����� �Է����ּ���.");
		}if(email.indexOf("@") > 14){
			errors.rejectValue("email", "", "���̸� �ʰ��Ͽ����ϴ�.");
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
