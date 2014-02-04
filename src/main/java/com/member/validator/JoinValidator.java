package com.member.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.member.domain.Member;



public class JoinValidator implements org.springframework.validation.Validator{

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
		String emailId = member.getEmailId();
		String pass = member.getPass();
		String conpass = member.getConpass();
		
		
		if(emailId == ""){
			errors.rejectValue("emailId", "", "이메일을 입력해주세요.");
		}if(emailId.indexOf("@") > 14){
			errors.rejectValue("emailId", "", "길이를 초과하였습니다.");
		}
		
		if(member.getName() == ""){
			errors.rejectValue("name", "", "이름을 입력해주세요");
		}if(member.getPass() == ""){
			errors.rejectValue("pass", "", "패스워드를 입력해주세요");
		}if(!pass.equals(conpass)){
			errors.rejectValue("conpass", "", "암호가 일치하지 않습니다.");
		}
	}


}
