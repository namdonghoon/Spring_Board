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
	private final int START_DEFAULT_PAGE = 0; //최소 시작 페이지 설정 [0부터 시작] 
	private MemberDao memberDao;
	
	public void memberDao(){}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	//Home 화면
		@RequestMapping(value = "/", method = RequestMethod.GET)
		public String form() {
			return "home";
		}
		@RequestMapping(value = "/home", method = RequestMethod.GET)
		public String home() {
			return "home";
		}
		
		//관리자 설정 : 아이디 설정에 따라 관리자 권한 설정 가능. 
		public String managerId(){
			String id="관리자";
			//추 후 관리자 기능 삽입 가능.
			return id; //관리자 권한 리턴 
		}
		
		//회원가입
		@RequestMapping(value="/saveMember", method= RequestMethod.POST)
		  public String save(
				  Model model, HttpSession session,
				  @ModelAttribute("commandJoin") Member member,
				  BindingResult errors) throws Exception{
			
			memberDao = new MemberDao();
			
			new JoinValidator().validate(member, errors);
			
			if(errors.hasErrors()){ 
				return "home"; //가입 실패.
			}else{
				session.setAttribute("emailId", member.getEmailId());
				memberDao.insertMember(member);
				new BoardController().BoardList(model, START_DEFAULT_PAGE);
				return "board/list";
			}
		  }
		
		// 가입 중복 아이디 체크
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
		
		//로그아웃
		@RequestMapping(value = "/logout", method = RequestMethod.GET)
		public String login(HttpSession session){
			session.invalidate();
			return "home";
		}
		
		//로그인
		@RequestMapping(value="/login", method= RequestMethod.POST)
		public String login(
				Model model, HttpSession session,
				@ModelAttribute("commandLogin") Member member,
				BindingResult errors) throws Exception{
			
			memberDao = new MemberDao();
			
			//DB값(memberDB)과 입력한값(member)과 비교 
			if(member.getEmailId() != ""){ //이메일을 입력했을 때 DB접근
				member = memberDao.checkMember(member.getEmailId(), member.getPass()); 
			}
			
			new LoginValidator().validate(member, errors);
			
			if(errors.hasErrors()){
				return "home"; //로그인 실패.
			}else{
				session.setAttribute("accessId", member.getEmailId());
				new BoardController().BoardList(model, START_DEFAULT_PAGE);
				return "board/list";
			}
		}
}
