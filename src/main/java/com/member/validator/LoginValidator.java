package com.member.validator;

import java.sql.SQLException;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.member.dao.MemberDao;
import com.member.domain.Member;



public class LoginValidator implements org.springframework.validation.Validator{
	
	
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

		//값을 입력안했을 때 빈공백 / DB에서의 찾은 값이 없을 때 null로 구분함
		if (member.getMember_Email() == "" ){
			errors.rejectValue("member_Email", "", "이메일을 입력해주세요.");
		}
		if (member.getMember_pass() == ""){
			errors.rejectValue("member_pass", "", "패스워드를 입력해주세요.");
		}
		if(member.getMember_Email() == null){
			errors.rejectValue("member_Email", "", "아이디/암호가 틀렸습니다.");
		}
	}


}
