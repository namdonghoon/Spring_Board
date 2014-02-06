package com.member.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.member.domain.Member;



public class IdCheckValidator implements org.springframework.validation.Validator{

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
		
		if(email != ""){
			errors.rejectValue("email", "", "�̹� �����ϴ� ���̵� �Դϴ�.");
		}
	}


}
