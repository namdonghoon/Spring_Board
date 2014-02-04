package com.member.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.member.domain.Board;
import com.member.domain.Member;



public class WriterValidator implements org.springframework.validation.Validator{

	//Validator�� �ش� Ŭ������ ���� �� ������ �����ϴ����� ���θ� �����Ѵ�.
	@Override
	public boolean supports(Class clazz) {
        if (Board.class.isAssignableFrom(clazz))
            return true;
        return false;
	}

	/*
	 * target ��ü�� ���� ������ �����Ѵ�. ���� ��� ������ ���� ��� 
	 * errors ��ü�� � ���������� ���� ������ �����Ѵ�.
	*/
	@Override
	public void validate(Object command, Errors errors) {
		Board board = (Board) command;
		
		if(board.getBoard_title() == ""){
			errors.rejectValue("board_title", "", "������ �Է����ּ���");
		}
		
	}


}
