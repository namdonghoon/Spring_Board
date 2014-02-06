package com.member.validator;

import java.sql.SQLException;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.member.dao.MemberDao;
import com.member.domain.Member;



public class LoginValidator implements org.springframework.validation.Validator{
	
	
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

		//���� �Է¾����� �� ����� / DB������ ã�� ���� ���� �� null�� ������
		if (member.getEmail() == "" ){
			errors.rejectValue("email", "", "�̸����� �Է����ּ���.");
		}
		if (member.getPass() == ""){
			errors.rejectValue("pass", "", "�н����带 �Է����ּ���.");
		}
		if(member.getEmail() == null){
			errors.rejectValue("email", "", "���̵�/��ȣ�� Ʋ�Ƚ��ϴ�.");
		}
	}


}
