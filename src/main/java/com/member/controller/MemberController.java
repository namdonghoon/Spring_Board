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
	
	private final int START_DEFAULT_PAGE = 0; //�ּ� ���� ������ ���� [0���� ����] 
	private final int WRITING_MAX_COUNT = 10;   //�� �������� �� �� 
	private final int PAGE_MAX_COUNT=5;   // ��ü ������ ��ȯ �� 
	
	@Inject
	private MemberDao memberDao;
	
	//ȸ������ 
	@RequestMapping(value="/save", method= RequestMethod.POST)
	public String save(
			@Valid @ModelAttribute("save") Member member, BindingResult result, 
			Model model, HttpSession session){
		
		//�� ���� 
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
	
	//���̵� �ߺ�Ȯ��.
	@RequestMapping(value = "/emailCheck", method = RequestMethod.GET)
	public String emailCheck(Model model, Member member){
		Member memberId = memberDao.idCheck(member.getEmail());
		if(memberId != null){ //�̸��� �ߺ�
			model.addAttribute("emailErrer", true);
			return "member/home";
		}else{ //�ߺ��ƴ� �� ���̵� ������.
			model.addAttribute("email", member.getEmail());
			return "member/home";
		}
	}
	//Ȯ�� �н����� üũ  
//	@RequestMapping(value = "/passCheck", method = RequestMethod.GET)
//	public String passCheck(Model model, Member member){
//		if(member.getPass().equals(member.getConpass())){
//			model.addAttribute("passErrer", true);
//			return "member/home";
//		}else{
//			return "member/home";
//		}
//	}
	
	//�α��� 
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
		@Valid @ModelAttribute("login") Member member, BindingResult result, 
		Model model, HttpSession session) {
		
		//�� ���� 
		if (result.hasErrors()) {
			return "member/home";
		}
		String resultURL = loginCheck(member, model, session);
		return resultURL;
	}
	
	//�� �α��� 
	@RequestMapping(value = "/loginRe", method = RequestMethod.POST)
	public String loginRe(
			@Valid @ModelAttribute("member") Member member, BindingResult result, 
			Model model, HttpSession session) {
		//�� ���� 
		if (result.hasErrors()) {
			return "member/login";
		}
		String resultURL = loginCheck(member, model, session);
		return resultURL;
	}
	
	//���̵�, ��ȣ üũ 
	public String loginCheck(Member member, Model model, HttpSession session){
		
		//���̵� üũ 
		Member memberId = memberDao.idCheck(member.getEmail());
		if(memberId == null){
			model.addAttribute("emailErrer", true);
			return "member/login";
		}
		
		//���̵��� ��ȣ üũ
		Member memberPass = memberDao.passCheck(memberId.getEmail(), member.getPass());
		if(memberPass == null){
			model.addAttribute("passErrer", true);
			return "member/login";
		}else{//���� ���� 
			session.setAttribute("accessId", memberPass.getEmail());
			model.addAttribute("member", memberPass);
			getList(model, START_DEFAULT_PAGE);
			return "board/list";
		}
	}
	
	//�α׾ƿ�
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session){
		session.invalidate();
		return "member/home";
	}
	
	
	
	
	//�Խ��� ����Ʈ  (����¡ó�� ���� )
	public void getList(Model model, int pageNum){
		
		int writeTotalCount = memberDao.totalPage(); //��ü �� ���� 
		int pageTotalCount = (writeTotalCount / WRITING_MAX_COUNT); //��ü ������ ��� 
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
		model.addAttribute("pageNum", pageNum); //������ �۹�ȣ ����
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("pageMaxCount", PAGE_MAX_COUNT);
	}
	

}
