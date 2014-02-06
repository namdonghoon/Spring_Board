package com.member.controller;

import javax.inject.Inject;
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
import com.member.dao.MemberDaoTest;
import com.member.domain.Member;
import com.member.validator.IdCheckValidator;
import com.member.validator.JoinValidator;
import com.member.validator.LoginValidator;


@Controller
@RequestMapping(value="/member")
public class MemberController {
	private final int START_DEFAULT_PAGE = 0; //�ּ� ���� ������ ���� [0���� ����] 
	
	@Inject
	private MemberDaoTest memberDaoTest;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String logintest(Member member, Model model, HttpSession session) {
		member = memberDaoTest.login(member.getEmail(), member.getPass());
		
		session.setAttribute("acceceId", member.getEmail());
		model.addAttribute(member);
		return "home";
	}
	
	@RequestMapping(value="/save", method= RequestMethod.POST)
	public String save(Member member, HttpSession session){
		memberDaoTest.save(member);
		session.setAttribute("acceceId", member.getEmail());
		return "home";
	}
	
	
	
	
	
//	
//	//Home ȭ��
//		@RequestMapping(value = "/", method = RequestMethod.GET)
//		public String form() {
//			return "home";
//		}
//		@RequestMapping(value = "/home", method = RequestMethod.GET)
//		public String home() {
//			return "home";
//		}
//		
//		//������ ���� : ���̵� ������ ���� ������ ���� ���� ����. 
//		public String managerId(){
//			String id="������";
//			//�� �� ������ ��� ���� ����.
//			return id; //������ ���� ���� 
//		}
//		
//		//ȸ������
//		@RequestMapping(value="/save", method= RequestMethod.POST)
//		  public String save(
//				  Model model, HttpSession session,
//				  @ModelAttribute("commandJoin") Member member,
//				  BindingResult errors) throws Exception{
//			
//			memberDao = new MemberDao();
//			
//			new JoinValidator().validate(member, errors);
//			
//			if(errors.hasErrors()){ 
//				return "home"; //���� ����.
//			}else{
//				session.setAttribute("email", member.getEmail());
//				memberDao.insertMember(member);
//				new BoardController().BoardList(model, START_DEFAULT_PAGE);
//				return "board/list";
//			}
//		  }
//		
//		// ���� �ߺ� ���̵� üũ
//		@RequestMapping(value="/idCheck", method= RequestMethod.GET)
//		public String idCheck(
//				Model model, 
//				@RequestParam String email,
//				@ModelAttribute("commandJoin") Member member,
//				BindingResult errors) throws Exception{
//			
//			memberDao = new MemberDao();
//			member = memberDao.idCheck(email);
//			if(member.getEmail() !=null){
//				new IdCheckValidator().validate(member, errors);
//			}
//			
//			if(errors.hasErrors()){
//				return "home";
//			}else{
//				model.addAttribute("email",email);
//				return "home";
//			}
//		}
//		
//		//�α׾ƿ�
//		@RequestMapping(value = "/logout", method = RequestMethod.GET)
//		public String logout(HttpSession session){
//			session.invalidate();
//			return "home";
//		}
//		
//		//�α���
//		@RequestMapping(value="/login", method= RequestMethod.POST)
//		public String login(
//				Model model, HttpSession session,
//				@ModelAttribute("commandLogin") Member member,
//				BindingResult errors) throws Exception{
//			
//			memberDao = new MemberDao();
//			
//			//DB��(memberDB)�� �Է��Ѱ�(member)�� �� 
//			if(member.getEmail() != ""){ //�̸����� �Է����� �� DB����
//				member = memberDao.checkMember(member.getEmail(), member.getPass()); 
//			}
//			
//			new LoginValidator().validate(member, errors);
//			
//			if(errors.hasErrors()){
//				return "home"; //�α��� ����.
//			}else{
//				session.setAttribute("accessId", member.getEmail());
//				new BoardController().BoardList(model, START_DEFAULT_PAGE);
//				return "board/list";
//			}
//		}
}
