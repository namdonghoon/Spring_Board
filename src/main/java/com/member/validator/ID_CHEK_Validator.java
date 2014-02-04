package com.member.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.member.domain.Member;



public class ID_CHEK_Validator implements org.springframework.validation.Validator{

	//Validator가 해당 클래스에 대한 값 검증을 지원하는지의 여부를 리턴한다.
	@Override
	public boolean supports(Class clazz) {
        if (Member.class.isAssignableFrom(clazz))
            return true;
        return false;
	}

	/*
	 * target 객체에 대한 검증을 실행한다. 검증 결과 문제가 있을 경우 
	 * errors 객체에 어떤 문제인지에 대한 정보를 저장한다.
	*/
	@Override
	public void validate(Object command, Errors errors) {
		Member member = (Member) command;
		String Email_id = member.getMember_Email();
		
		if(Email_id != ""){
			errors.rejectValue("member_Email", "", "이미 존재하는 아이디 입니다.");
		}
	}


}
