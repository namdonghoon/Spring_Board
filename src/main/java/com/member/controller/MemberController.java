package com.member.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.member.dao.BoardDaoTest;
import com.member.dao.MemberDao;
import com.member.dao.MemberDaoTest;
import com.member.domain.Board;
import com.member.domain.Member;
import com.member.validator.IdCheckValidator;
import com.member.validator.JoinValidator;
import com.member.validator.LoginValidator;


@Controller
@RequestMapping(value="/member")
public class MemberController {	
	
	private final int START_DEFAULT_PAGE = 0; //최소 시작 페이지 설정 [0부터 시작] 
	private final int WRITING_MAX_COUNT = 10;   //한 페이지당 글 수 
	private final int PAGE_MAX_COUNT=5;   // 전체 페이지 전환 수 
	
	@Inject
	private MemberDaoTest memberDaoTest;
	
	//로그인 
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
		@Valid @ModelAttribute("member") Member member, BindingResult result, 
		Model model, HttpSession session) {
		
		if (result.hasErrors()) {
			return "home";//
		} else {
			//member = memberDaoTest.login(member.getEmail(), member.getPass());
			//session.setAttribute("accessId", member.getEmail());
			return "home";
			//list(model, START_DEFAULT_PAGE);
			//return "board/list";
		}
	}

	
	//로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session){
		session.invalidate();
		return "home";
	}
	
	
	//회원가입 
	@RequestMapping(value="/save", method= RequestMethod.POST)
	public String save(
			Member member, Model model, HttpSession session){
		memberDaoTest.save(member);
		session.setAttribute("accessId", member.getEmail());
		list(model, START_DEFAULT_PAGE);
		return "board/list";
	}
	
	
	//게시판 리스트  (페이징처리 포함 )
	public void list(Model model, int pageNum){
		
		int writeTotalCount = memberDaoTest.totalPage(); //전체 글 개수 
		int pageTotalCount = (writeTotalCount / WRITING_MAX_COUNT); //전체 페이지 계산 
		int startPage = (pageNum/PAGE_MAX_COUNT) *PAGE_MAX_COUNT +1; //
	
		int endPage=0;
		//ex) 1, 1~2, 1~3 ... 1~9
		if(pageTotalCount-(startPage-1) < PAGE_MAX_COUNT){
			endPage = (startPage) + (pageTotalCount % PAGE_MAX_COUNT);
		}else{  //ex) 1~10, 11~20, 21~30
			endPage = (startPage-1) + PAGE_MAX_COUNT;
		}
		
		int writeStarNum = ((pageNum) * WRITING_MAX_COUNT);
		List<Board> list = memberDaoTest.list(writeStarNum, WRITING_MAX_COUNT);
		model.addAttribute("list", list);
		model.addAttribute("pageNum", pageNum); //선택한 글번호 전송
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("pageMaxCount", PAGE_MAX_COUNT);
	}
	
	
//	
//	//Home 화면
//		@RequestMapping(value = "/", method = RequestMethod.GET)
//		public String form() {
//			return "home";
//		}
//		@RequestMapping(value = "/home", method = RequestMethod.GET)
//		public String home() {
//			return "home";
//		}
//		
//		//관리자 설정 : 아이디 설정에 따라 관리자 권한 설정 가능. 
//		public String managerId(){
//			String id="관리자";
//			//추 후 관리자 기능 삽입 가능.
//			return id; //관리자 권한 리턴 
//		}
//		
//		//회원가입
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
//				return "home"; //가입 실패.
//			}else{
//				session.setAttribute("email", member.getEmail());
//				memberDao.insertMember(member);
//				new BoardController().BoardList(model, START_DEFAULT_PAGE);
//				return "board/list";
//			}
//		  }
//		
//		// 가입 중복 아이디 체크
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
//		//로그아웃
//		@RequestMapping(value = "/logout", method = RequestMethod.GET)
//		public String logout(HttpSession session){
//			session.invalidate();
//			return "home";
//		}
//		
//		//로그인
//		@RequestMapping(value="/login", method= RequestMethod.POST)
//		public String login(
//				Model model, HttpSession session,
//				@ModelAttribute("commandLogin") Member member,
//				BindingResult errors) throws Exception{
//			
//			memberDao = new MemberDao();
//			
//			//DB값(memberDB)과 입력한값(member)과 비교 
//			if(member.getEmail() != ""){ //이메일을 입력했을 때 DB접근
//				member = memberDao.checkMember(member.getEmail(), member.getPass()); 
//			}
//			
//			new LoginValidator().validate(member, errors);
//			
//			if(errors.hasErrors()){
//				return "home"; //로그인 실패.
//			}else{
//				session.setAttribute("accessId", member.getEmail());
//				new BoardController().BoardList(model, START_DEFAULT_PAGE);
//				return "board/list";
//			}
//		}
}
