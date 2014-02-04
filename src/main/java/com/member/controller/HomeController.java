package com.member.controller;

import java.util.List;
import java.util.Locale;

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
import com.member.domain.Board;
import com.member.domain.Member;
import com.member.validator.ID_CHEK_Validator;
import com.member.validator.JoinValidator;
import com.member.validator.LoginValidator;
import com.member.validator.WriterValidator;

@Controller
public class HomeController {
	private final int START_DEFAULT_PAGE = 0; //최소 시작 페이지 설정 [0부터 시작] 
	private final int WRITING_MAX_COUNT = 10;   //한 페이지당 글 수 
	private final int PAGE_MAX_COUNT=5;   // 전체 페이지 전환 수 
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private MemberDao memberDao;

	public void memberDao(){}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	//Home 화면
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String default_home() {
		return "home";
	}
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	//관리자 설정 : 아이디 설정에 따라 관리자 권한 설정 가능. 
	public String manager_id(){
		String id="관리자";
		//추 후 관리자 기능 삽입 가능.
		return id; //관리자 권한 리턴 
	}
	
	//글목록 : 페이징처리 포함
	@RequestMapping(value = "/board_list", method = RequestMethod.POST)
	public String boardlistPOST() {
		return "board_list";
	}
	@RequestMapping(value = "/board_list", method = RequestMethod.GET)
	public String boardlistGET(
			@RequestParam int pageNum,
			Model model) throws Exception {
		
		//limit가 0부터 시작하므로 페이지 1에서 0으로 만들어주기 위해 1을 뺌. 
		Board_list(model, pageNum-1);
		return "board_list";
	}
	public void Board_list(Model model, int pageNum) throws Exception{
		memberDao = new MemberDao();
		
		int wrTotalCount = memberDao.getBoardCount(); //전체 글 개수 
		int pgTotalCount = (wrTotalCount / WRITING_MAX_COUNT); //전체 페이지 계산 
		int startPage = (pageNum/PAGE_MAX_COUNT) *PAGE_MAX_COUNT +1; //
	
		int endPage=0;
		//ex) 1, 1~2, 1~3 ... 1~9
		if(pgTotalCount-(startPage-1) < PAGE_MAX_COUNT){
			endPage = (startPage) + (pgTotalCount % PAGE_MAX_COUNT);
		}else{ //ex) 1~10, 11~20, 21~30
			endPage = (startPage-1) + PAGE_MAX_COUNT;
		}
		
		int wrStarNum = ((pageNum) * WRITING_MAX_COUNT);
		//시작글 번호 = 선택한 페이지번호 * 페이지표시 최대 글 수
		List<Board> board_list = memberDao.getBoard_list(wrStarNum, WRITING_MAX_COUNT);

		model.addAttribute("list", board_list);
		model.addAttribute("pageNum", pageNum); //선택한 글번호 전송
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pgTotalCount", pgTotalCount);
		model.addAttribute("pgMaxCount", PAGE_MAX_COUNT);
	}
	
	
	//글쓰기
	@RequestMapping(value = "/board_insert", method = RequestMethod.GET)
	public String boardinsert() throws Exception {
		return "board_insert";
	}
	@RequestMapping(value = "/board_insert", method = RequestMethod.POST)
	public String boardinsertOK(
			Model model,
			HttpSession session,
			@ModelAttribute("command_writer") Board board,
			BindingResult errors) throws Exception {
		memberDao = new MemberDao();
	
		String Email_id = (String)session.getAttribute("Email_id");
		board.setBoard_Email(Email_id);
		new WriterValidator().validate(board, errors);
		
		if(errors.hasErrors()){
			return "board_insert";
		}else{
			memberDao.insertBoard(board);
			Board_list(model, START_DEFAULT_PAGE);
			return "board_list";
		}
	}
	
	
	//회원가입
	@RequestMapping(value="/member_insert", method= RequestMethod.POST)
	  public String member_insertOK(
			  Model model,
			  HttpSession session,
			  @ModelAttribute("command_join") Member member,
			  BindingResult errors) throws Exception{
		
		memberDao = new MemberDao();
		
		new JoinValidator().validate(member, errors);
		
		if(errors.hasErrors()){ 
			return "home"; //가입 실패.
		}else{
			memberDao.insertMember(member);
			logger.info("가입 완료했습니다!~!");
			session.setAttribute("Email_id", member.getMember_Email());
			Board_list(model, START_DEFAULT_PAGE);
			return "board_list";
		}
	  }
	
	// 가입 중복 아이디 체크
	@RequestMapping(value="/id_check", method= RequestMethod.GET)
	public String id_check(
			Model model, 
			@RequestParam String member_Email,
			@ModelAttribute("command_join") Member member,
			BindingResult errors) throws Exception{
		
		memberDao = new MemberDao();
		member = memberDao.id_check(member_Email);
		if(member.getMember_Email() !=null){
			new ID_CHEK_Validator().validate(member, errors);
		}
		
		if(errors.hasErrors()){
			return "home";
		}else{
			model.addAttribute("member_Email",member_Email);
			return "home";
		}
	}
	
	//글 조회 : 관리자 id. 뷰로 전송 
	@RequestMapping(value = "/board_check", method = RequestMethod.GET)
	public String board_check(
			Model model, @RequestParam int board_num) throws Exception{
		memberDao = new MemberDao();
		Board board = memberDao.getBoard(board_num);
		model.addAttribute("board", board);
		model.addAttribute("manager_id", manager_id());
		return "board_check";
	}
	
	
	//글 삭제 - 세션 아이디가 일치한지 비교한 후 권한 설정.
	@RequestMapping(value = "/board_delete", method = RequestMethod.POST)
	public String board_delete(
			Model model, Board board,
			HttpSession session) throws Exception{
		
		memberDao = new MemberDao();
		String Email_id = session.getAttribute("Email_id").toString();
		
		//권한 부여 : 관리자 모드로 접속한다면.
		if(manager_id().equals(Email_id)){
			/** 세션 id를 선택한 글의 id로 설정 
			  * 세션 id와 선택한 글 id와 계속 일치 */
			Email_id = board.getBoard_Email();
		}
		memberDao.deleteBoard(board.getBoard_num(), Email_id);
		Board_list(model, START_DEFAULT_PAGE);
		return "board_list";
	}
	
	
	//글 수정 - 세션 아이디가 일치한지 비교한 후 권한 설정. 
	@RequestMapping(value = "/board_update", method = RequestMethod.POST)
	public String board_update(
			Model model, Board board,
			HttpSession session) throws Exception{
		
		memberDao = new MemberDao();
		String Email_id = session.getAttribute("Email_id").toString();
		
		//권한 부여 : 관리자 모드로 접속한다면.
		if(manager_id().equals(Email_id)){
			/** 세션 id를 선택한 글의 id로 설정 
			  * 세션 id와 선택한 글 id와 계속 일치 */
			Email_id = board.getBoard_Email();
		}
		memberDao.updateBoard(board, Email_id);
		Board_list(model, START_DEFAULT_PAGE);
		return "board_list";
	}
	
	
	
	//로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String login(HttpSession session){
		session.invalidate();
		return "home";
	}
	
	//로그인
	@RequestMapping(value="/login", method= RequestMethod.POST)
	public String loginOK(
			Model model, HttpSession session,
			@ModelAttribute("command_login") Member member,
			BindingResult errors) throws Exception{
		
		memberDao = new MemberDao();
			
		//DB값(memberDB)과 입력한값(member)과 비교 
		if(member.getMember_Email() != ""){ //이메일을 입력했을 때 DB접근
			Member memberDB = memberDao.getMember(member.getMember_Email(), member.getMember_pass()); 
			new LoginValidator().validate(memberDB, errors);
		}else{ //입력안했을 때 입력안한값 체크
			new LoginValidator().validate(member, errors);
		}
		
		
		if(errors.hasErrors()){
			return "home"; //로그인 실패.
		}else{
			logger.info("로그인 완료!");
			session.setAttribute("Email_id", member.getMember_Email());
			Board_list(model, START_DEFAULT_PAGE);
			return "board_list";
		}
	}
	

	
	
}
