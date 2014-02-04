package com.member.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.member.dao.MemberDao;
import com.member.domain.Member;
import com.member.validator.IdCheckValidator;
import com.member.validator.JoinValidator;
import com.member.validator.LoginValidator;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private final int START_DEFAULT_PAGE = 0; //�ּ� ���� ������ ���� [0���� ����] 
	private MemberDao memberDao;

	public MemberController(){
		System.out.println("MemberController");
	}
	
	public void memberDao(){}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	//Home ȭ��
		@RequestMapping(value = "/", method = RequestMethod.GET)
		public String form() {
			return "home";
		}
		@RequestMapping(value = "/home", method = RequestMethod.GET)
		public String home() {
			return "home";
		}
		
		//������ ���� : ���̵� ������ ���� ������ ���� ���� ����. 
		public String managerId(){
			String id="������";
			//�� �� ������ ��� ���� ����.
			return id; //������ ���� ���� 
		}
		
		//ȸ������
		@RequestMapping(value="/saveMember", method= RequestMethod.POST)
		  public String save(
				  Model model, HttpSession session,
				  @ModelAttribute("commandJoin") Member member,
				  BindingResult errors) throws Exception{
			
			memberDao = new MemberDao();
			
			new JoinValidator().validate(member, errors);
			
			if(errors.hasErrors()){ 
				return "home"; //���� ����.
			}else{
				session.setAttribute("emailId", member.getEmailId());
				memberDao.insertMember(member);
				new BoardController().BoardList(model, START_DEFAULT_PAGE);
				return "board/list";
			}
		  }
		
		// ���� �ߺ� ���̵� üũ
		@RequestMapping(value="/memberIdCheck", method= RequestMethod.GET)
		public String idCheck(
				Model model, 
				@RequestParam String emailId,
				@ModelAttribute("commandJoin") Member member,
				BindingResult errors) throws Exception{
			
			memberDao = new MemberDao();
			member = memberDao.idCheck(emailId);
			if(member.getEmailId() !=null){
				new IdCheckValidator().validate(member, errors);
			}
			
			if(errors.hasErrors()){
				return "home";
			}else{
				model.addAttribute("emailId",emailId);
				return "home";
			}
		}
		
		//�α׾ƿ�
		@RequestMapping(value = "/logout", method = RequestMethod.GET)
		public String login(HttpSession session){
			session.invalidate();
			return "home";
		}
		
		//�α���
		@RequestMapping(value="/login", method= RequestMethod.POST)
		public String loginOK(
				Model model, HttpSession session,
				@ModelAttribute("commandLogin") Member member,
				BindingResult errors) throws Exception{
			
			memberDao = new MemberDao();
				
			//DB��(memberDB)�� �Է��Ѱ�(member)�� �� 
			if(member.getEmailId() != ""){ //�̸����� �Է����� �� DB����
				Member memberDB = memberDao.getMember(member.getEmailId(), member.getPass()); 
				new LoginValidator().validate(memberDB, errors);
			}else{ //�Է¾����� �� �Է¾��Ѱ� üũ
				new LoginValidator().validate(member, errors);
			}
			
			
			if(errors.hasErrors()){
				return "home"; //�α��� ����.
			}else{
				session.setAttribute("accessId", member.getEmailId());
				new BoardController().BoardList(model, START_DEFAULT_PAGE);
				return "board/list";
			}
		}
}
