package com.member.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.member.dao.MemberDao;
import com.member.domain.Board;
import com.member.domain.Member;


@Controller
@RequestMapping(value="/member")
public class MemberController {	
	
	private final int START_DEFAULT_PAGE = 0; //최소 시작 페이지 설정 [0부터 시작] 
	private final int WRITING_MAX_COUNT = 10;   //한 페이지당 글 수 
	private final int PAGE_MAX_COUNT=5;   // 전체 페이지 전환 수 
	
	@Inject
	private MemberDao memberDao;
	
	//회원가입 
	@RequestMapping(value="/save", method= RequestMethod.POST)
	public String save(
			@Valid @ModelAttribute("save") Member member, BindingResult result, 
			Model model, HttpSession session){
		
		//폼 검증 
		if (result.hasErrors()) {
			return "member/home";
		}
		
		memberDao.save(member);
		session.setAttribute("accessId", member.getEmail());
		getList(model, START_DEFAULT_PAGE);
		return "board/list";
	}
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() { 
		return "member/home";
	}
	
	//아이디 중복확인.
	@RequestMapping(value = "/emailCheck", method = RequestMethod.GET)
	public String emailCheck(Model model, Member member){
		Member memberId = memberDao.idCheck(member.getEmail());
		if(memberId != null){ //이메일 중복
			model.addAttribute("emailErrer", true);
			return "member/home";
		}else{ //중복아닐 시 아이디를 가져감.
			model.addAttribute("email", member.getEmail());
			return "member/home";
		}
	}
	//확인 패스워드 체크  
//	@RequestMapping(value = "/passCheck", method = RequestMethod.GET)
//	public String passCheck(Model model, Member member){
//		if(member.getPass().equals(member.getConpass())){
//			model.addAttribute("passErrer", true);
//			return "member/home";
//		}else{
//			return "member/home";
//		}
//	}
	
	//로그인 
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
		@Valid @ModelAttribute("login") Member member, BindingResult result, 
		Model model, HttpSession session) {
		
		//폼 검증 
		if (result.hasErrors()) {
			return "member/home";
		}
		String resultURL = loginCheck(member, model, session);
		return resultURL;
	}
	
	//재 로그인 
	@RequestMapping(value = "/loginRe", method = RequestMethod.POST)
	public String loginRe(
			@Valid @ModelAttribute("member") Member member, BindingResult result, 
			Model model, HttpSession session) {
		//폼 검증 
		if (result.hasErrors()) {
			return "member/login";
		}
		String resultURL = loginCheck(member, model, session);
		return resultURL;
	}
	
	//아이디, 암호 체크 
	public String loginCheck(Member member, Model model, HttpSession session){
		
		//아이디 체크 
		Member memberId = memberDao.idCheck(member.getEmail());
		if(memberId == null){
			model.addAttribute("emailErrer", true);
			return "member/login";
		}
		
		//아이디의 암호 체크
		Member memberPass = memberDao.passCheck(memberId.getEmail(), member.getPass());
		if(memberPass == null){
			model.addAttribute("passErrer", true);
			return "member/login";
		}else{//정상 접속 
			session.setAttribute("accessId", memberPass.getEmail());
			model.addAttribute("member", memberPass);
			getList(model, START_DEFAULT_PAGE);
			return "board/list";
		}
	}
	
	//로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session){
		session.invalidate();
		return "member/home";
	}
	
	
	
	
	//게시판 리스트  (페이징처리 포함 )
	public void getList(Model model, int pageNum){
		
		int writeTotalCount = memberDao.totalPage(); //전체 글 개수 
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
		List<Board> list = memberDao.list(writeStarNum, WRITING_MAX_COUNT);
		model.addAttribute("list", list);
		model.addAttribute("pageNum", pageNum); //선택한 글번호 전송
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("pageMaxCount", PAGE_MAX_COUNT);
	}
	

}
