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
	private final int START_DEFAULT_PAGE = 0; //�ּ� ���� ������ ���� [0���� ����] 
	private final int WRITING_MAX_COUNT = 10;   //�� �������� �� �� 
	private final int PAGE_MAX_COUNT=5;   // ��ü ������ ��ȯ �� 
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private MemberDao memberDao;

	public void memberDao(){}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	//Home ȭ��
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String default_home() {
		return "home";
	}
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	//������ ���� : ���̵� ������ ���� ������ ���� ���� ����. 
	public String manager_id(){
		String id="������";
		//�� �� ������ ��� ���� ����.
		return id; //������ ���� ���� 
	}
	
	//�۸�� : ����¡ó�� ����
	@RequestMapping(value = "/board_list", method = RequestMethod.POST)
	public String boardlistPOST() {
		return "board_list";
	}
	@RequestMapping(value = "/board_list", method = RequestMethod.GET)
	public String boardlistGET(
			@RequestParam int pageNum,
			Model model) throws Exception {
		
		//limit�� 0���� �����ϹǷ� ������ 1���� 0���� ������ֱ� ���� 1�� ��. 
		Board_list(model, pageNum-1);
		return "board_list";
	}
	public void Board_list(Model model, int pageNum) throws Exception{
		memberDao = new MemberDao();
		
		int wrTotalCount = memberDao.getBoardCount(); //��ü �� ���� 
		int pgTotalCount = (wrTotalCount / WRITING_MAX_COUNT); //��ü ������ ��� 
		int startPage = (pageNum/PAGE_MAX_COUNT) *PAGE_MAX_COUNT +1; //
	
		int endPage=0;
		//ex) 1, 1~2, 1~3 ... 1~9
		if(pgTotalCount-(startPage-1) < PAGE_MAX_COUNT){
			endPage = (startPage) + (pgTotalCount % PAGE_MAX_COUNT);
		}else{ //ex) 1~10, 11~20, 21~30
			endPage = (startPage-1) + PAGE_MAX_COUNT;
		}
		
		int wrStarNum = ((pageNum) * WRITING_MAX_COUNT);
		//���۱� ��ȣ = ������ ��������ȣ * ������ǥ�� �ִ� �� ��
		List<Board> board_list = memberDao.getBoard_list(wrStarNum, WRITING_MAX_COUNT);

		model.addAttribute("list", board_list);
		model.addAttribute("pageNum", pageNum); //������ �۹�ȣ ����
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pgTotalCount", pgTotalCount);
		model.addAttribute("pgMaxCount", PAGE_MAX_COUNT);
	}
	
	
	//�۾���
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
	
	
	//ȸ������
	@RequestMapping(value="/member_insert", method= RequestMethod.POST)
	  public String member_insertOK(
			  Model model,
			  HttpSession session,
			  @ModelAttribute("command_join") Member member,
			  BindingResult errors) throws Exception{
		
		memberDao = new MemberDao();
		
		new JoinValidator().validate(member, errors);
		
		if(errors.hasErrors()){ 
			return "home"; //���� ����.
		}else{
			memberDao.insertMember(member);
			logger.info("���� �Ϸ��߽��ϴ�!~!");
			session.setAttribute("Email_id", member.getMember_Email());
			Board_list(model, START_DEFAULT_PAGE);
			return "board_list";
		}
	  }
	
	// ���� �ߺ� ���̵� üũ
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
	
	//�� ��ȸ : ������ id. ��� ���� 
	@RequestMapping(value = "/board_check", method = RequestMethod.GET)
	public String board_check(
			Model model, @RequestParam int board_num) throws Exception{
		memberDao = new MemberDao();
		Board board = memberDao.getBoard(board_num);
		model.addAttribute("board", board);
		model.addAttribute("manager_id", manager_id());
		return "board_check";
	}
	
	
	//�� ���� - ���� ���̵� ��ġ���� ���� �� ���� ����.
	@RequestMapping(value = "/board_delete", method = RequestMethod.POST)
	public String board_delete(
			Model model, Board board,
			HttpSession session) throws Exception{
		
		memberDao = new MemberDao();
		String Email_id = session.getAttribute("Email_id").toString();
		
		//���� �ο� : ������ ���� �����Ѵٸ�.
		if(manager_id().equals(Email_id)){
			/** ���� id�� ������ ���� id�� ���� 
			  * ���� id�� ������ �� id�� ��� ��ġ */
			Email_id = board.getBoard_Email();
		}
		memberDao.deleteBoard(board.getBoard_num(), Email_id);
		Board_list(model, START_DEFAULT_PAGE);
		return "board_list";
	}
	
	
	//�� ���� - ���� ���̵� ��ġ���� ���� �� ���� ����. 
	@RequestMapping(value = "/board_update", method = RequestMethod.POST)
	public String board_update(
			Model model, Board board,
			HttpSession session) throws Exception{
		
		memberDao = new MemberDao();
		String Email_id = session.getAttribute("Email_id").toString();
		
		//���� �ο� : ������ ���� �����Ѵٸ�.
		if(manager_id().equals(Email_id)){
			/** ���� id�� ������ ���� id�� ���� 
			  * ���� id�� ������ �� id�� ��� ��ġ */
			Email_id = board.getBoard_Email();
		}
		memberDao.updateBoard(board, Email_id);
		Board_list(model, START_DEFAULT_PAGE);
		return "board_list";
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
			@ModelAttribute("command_login") Member member,
			BindingResult errors) throws Exception{
		
		memberDao = new MemberDao();
			
		//DB��(memberDB)�� �Է��Ѱ�(member)�� �� 
		if(member.getMember_Email() != ""){ //�̸����� �Է����� �� DB����
			Member memberDB = memberDao.getMember(member.getMember_Email(), member.getMember_pass()); 
			new LoginValidator().validate(memberDB, errors);
		}else{ //�Է¾����� �� �Է¾��Ѱ� üũ
			new LoginValidator().validate(member, errors);
		}
		
		
		if(errors.hasErrors()){
			return "home"; //�α��� ����.
		}else{
			logger.info("�α��� �Ϸ�!");
			session.setAttribute("Email_id", member.getMember_Email());
			Board_list(model, START_DEFAULT_PAGE);
			return "board_list";
		}
	}
	

	
	
}
