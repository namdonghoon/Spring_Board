package com.member.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.member.dao.BoardDao;
import com.member.domain.Board;
import com.member.validator.WriterValidator;

// http://localhost:8080/board/list

@Controller
@RequestMapping(value="/board")
public class BoardController {
	private final int START_DEFAULT_PAGE = 0; //�ּ� ���� ������ ���� [0���� ����] 
	private final int WRITING_MAX_COUNT = 10;   //�� �������� �� �� 
	private final int PAGE_MAX_COUNT=5;   // ��ü ������ ��ȯ �� 
	private BoardDao boardDao;
	
	public void boardDao(){}

	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}


	//�۸�� : ����¡ó�� ����
	@RequestMapping(value = "/boardList", method = RequestMethod.POST)
	public String list() {
		return "board/list";
	}
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String list(@RequestParam int pageNum, Model model) throws Exception {
		
		//limit�� 0���� �����ϹǷ� ������ 1���� 0���� ������ֱ� ���� 1�� ��. 
		BoardList(model, pageNum-1);
		return "board/list";
	}
	public void BoardList(Model model, int pageNum) throws Exception{
		boardDao = new BoardDao();
		
		int writeTotalCount = boardDao.totalCount(); //��ü �� ���� 
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
		List<Board> boardList = boardDao.list(writeStarNum, WRITING_MAX_COUNT);

		model.addAttribute("list", boardList);
		model.addAttribute("pageNum", pageNum); //������ �۹�ȣ ����
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("pageMaxCount", PAGE_MAX_COUNT);
	}
	
	
	//�۾���
	@RequestMapping(value = "/saveBoard", method = RequestMethod.GET)
	public String save() throws Exception {
		return "board/save";
	}
	@RequestMapping(value = "/saveBoard", method = RequestMethod.POST)
	public String save(
			Model model,
			HttpSession session,
			@ModelAttribute("commandWriter") Board board,
			BindingResult errors) throws Exception {
		boardDao = new BoardDao();
	
		String accessId = (String)session.getAttribute("accessId");
		board.setEmail(accessId);
		new WriterValidator().validate(board, errors);
		
		if(errors.hasErrors()){
			return "board/save";
		}else{
			boardDao.save(board);
			BoardList(model, START_DEFAULT_PAGE);
			return "board/list";
		}
	}
	
	
	
	
	//�� ��ȸ : ������ id. ��� ���� 
	@RequestMapping(value = "/checkBoard", method = RequestMethod.GET)
	public String check(
			Model model, @RequestParam int numberId) throws Exception{
		boardDao = new BoardDao();
		Board board = boardDao.check(numberId);
		model.addAttribute("board", board);
		model.addAttribute("managerId", new MemberController().managerId());
		return "board/check";
	}
	
	
	//�� ���� - ���� ���̵� ��ġ���� ���� �� ���� ����.
	@RequestMapping(value = "/deleteBoard", method = RequestMethod.POST)
	public String delete(
			Model model, Board board,
			HttpSession session) throws Exception{
		
		boardDao = new BoardDao();
		String accessId = session.getAttribute("accessId").toString();
		
		//���� �ο� : ������ ���� �����Ѵٸ�.
		if(new MemberController().managerId().equals(accessId)){
			/** ���� id�� ������ ���� id�� ���� 
			  * ���� id�� ������ �� id�� ��� ��ġ */
			accessId = board.getEmail();
		}
		boardDao.delete(board.getNumberId(), accessId);
		BoardList(model, START_DEFAULT_PAGE);
		return "board/list";
	}
	
	
	//�� ���� - ���� ���̵� ��ġ���� ���� �� ���� ����. 
	@RequestMapping(value = "/updateBoard", method = RequestMethod.POST)
	public String update(
			Model model, Board board,
			HttpSession session) throws Exception{
		
		boardDao = new BoardDao();
		String accessId = session.getAttribute("accessId").toString();
		
		//���� �ο� : ������ ���� �����Ѵٸ�.
		if(new MemberController().managerId().equals(accessId)){
			/** ���� id�� ������ ���� id�� ���� 
			  * ���� id�� ������ �� id�� ��� ��ġ */
			accessId = board.getEmail();
		}
		boardDao.update(board, accessId);
		BoardList(model, START_DEFAULT_PAGE);
		return "board/list";
	}
	
	
	
	
	

	
	
}
