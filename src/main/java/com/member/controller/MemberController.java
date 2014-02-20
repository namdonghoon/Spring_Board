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
	
	private final int START_DEFAULT_PAGE = 0; //占쌍쇽옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 [0占쏙옙占쏙옙 占쏙옙占쏙옙] 
	private final int WRITING_MAX_COUNT = 10;   //占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙 占쏙옙 
	private final int PAGE_MAX_COUNT=5;   // 占쏙옙체 占쏙옙占쏙옙占쏙옙 占쏙옙환 占쏙옙 
	
	@Inject
	private MemberDao memberDao;
	
	//회占쏙옙占쏙옙 
	@RequestMapping(value="/save", method= RequestMethod.POST)
	public String save(
			@Valid @ModelAttribute("save") Member member, BindingResult result, 
			Model model, HttpSession session){
		
		//占쏙옙 占쏙옙占쏙옙 
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
	
	//占쏙옙占싱듸옙 占쌩븝옙확占쏙옙.
	@RequestMapping(value = "/emailCheck", method = RequestMethod.GET)
	public String emailCheck(Model model, Member member){
		Member memberId = memberDao.idCheck(member.getEmail());
		if(memberId != null){ //占싱몌옙占쏙옙 占쌩븝옙
			model.addAttribute("emailErrer", true);
			return "member/home";
		}else{ //占쌩븝옙占싣댐옙 占쏙옙 占쏙옙占싱듸옙 占쏙옙占쏙옙占쏙옙.
			model.addAttribute("email", member.getEmail());
			return "member/home";
		}
	}
	//확占쏙옙 占싻쏙옙占쏙옙占쏙옙 체크  
//	@RequestMapping(value = "/passCheck", method = RequestMethod.GET)
//	public String passCheck(Model model, Member member){
//		if(member.getPass().equals(member.getConpass())){
//			model.addAttribute("passErrer", true);
//			return "member/home";
//		}else{
//			return "member/home";
//		}
//	}
	
	//占싸깍옙占쏙옙 
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
		@Valid @ModelAttribute("login") Member member, BindingResult result, 
		Model model, HttpSession session) { 
		//占쏙옙 占쏙옙占쏙옙 
		if (result.hasErrors()) {
			return "member/home";
		}
		String resultURL = loginCheck(member, model, session);
		return resultURL;
	}
	
	//占쏙옙 占싸깍옙占쏙옙 
	@RequestMapping(value = "/loginRe", method = RequestMethod.POST)
	public String loginRe(
			@Valid @ModelAttribute("login") Member member, BindingResult result, 
			Model model, HttpSession session) {
		//占쏙옙 占쏙옙占쏙옙 
		if (result.hasErrors()) {
			return "member/login";
		}
		String resultURL = loginCheck(member, model, session);
		return resultURL;
	}
	
	//占쏙옙占싱듸옙, 占쏙옙호 체크 
	public String loginCheck(Member member, Model model, HttpSession session){
		
		//占쏙옙占싱듸옙 체크 
		Member memberId = memberDao.idCheck(member.getEmail());
		if(memberId == null){
			model.addAttribute("emailErrer", true);
			return "member/login";
		}
		
		//占쏙옙占싱듸옙占쏙옙 占쏙옙호 체크
		Member memberPass = memberDao.passCheck(memberId.getEmail(), member.getPass());
		if(memberPass == null){
			model.addAttribute("passErrer", true);
			return "member/login";
		}else{//占쏙옙占쏙옙 占쏙옙占쏙옙 
			session.setAttribute("accessId", memberPass.getEmail());
			model.addAttribute("member", memberPass);
			getList(model, START_DEFAULT_PAGE);
			return "board/list";
		}
	}
	
	//占싸그아울옙
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session){
		session.invalidate();
		return "member/home";
	}
	
	
	
	
	//占쌉쏙옙占쏙옙 占쏙옙占쏙옙트  (占쏙옙占쏙옙징처占쏙옙 占쏙옙占쏙옙 )
	public void getList(Model model, int pageNum){
		
		int writeTotalCount = memberDao.totalPage(); //占쏙옙체 占쏙옙 占쏙옙占쏙옙 
		int pageTotalCount = (writeTotalCount / WRITING_MAX_COUNT); //占쏙옙체 占쏙옙占쏙옙占쏙옙 占쏙옙占�
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
		model.addAttribute("pageNum", pageNum); //占쏙옙占쏙옙占쏙옙 占쌜뱄옙호 占쏙옙占�		
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("pageMaxCount", PAGE_MAX_COUNT);
	}
	

}
