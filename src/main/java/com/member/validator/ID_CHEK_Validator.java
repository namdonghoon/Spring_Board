package com.member.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.member.domain.Member;



public class ID_CHEK_Validator implements org.springframework.validation.Validator{

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
		
		if(Email_id != ""){
			errors.rejectValue("member_Email", "", "�̹� �����ϴ� ���̵� �Դϴ�.");
		}
	}


}
