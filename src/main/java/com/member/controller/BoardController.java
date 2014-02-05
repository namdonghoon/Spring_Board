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
	private final int START_DEFAULT_PAGE = 0; //최소 시작 페이지 설정 [0부터 시작] 
	private final int WRITING_MAX_COUNT = 10;   //한 페이지당 글 수 
	private final int PAGE_MAX_COUNT=5;   // 전체 페이지 전환 수 
	private BoardDao boardDao;
	
	public void boardDao(){}

	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}


	//글목록 : 페이징처리 포함
	@RequestMapping(value = "/boardList", method = RequestMethod.POST)
	public String list() {
		return "board/list";
	}
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String list(@RequestParam int pageNum, Model model) throws Exception {
		
		//limit가 0부터 시작하므로 페이지 1에서 0으로 만들어주기 위해 1을 뺌. 
		BoardList(model, pageNum-1);
		return "board/list";
	}
	public void BoardList(Model model, int pageNum) throws Exception{
		boardDao = new BoardDao();
		
		int writeTotalCount = boardDao.totalCount(); //전체 글 개수 
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
		List<Board> boardList = boardDao.list(writeStarNum, WRITING_MAX_COUNT);

		model.addAttribute("list", boardList);
		model.addAttribute("pageNum", pageNum); //선택한 글번호 전송
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("pageMaxCount", PAGE_MAX_COUNT);
	}
	
	
	//글쓰기
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
	
	
	
	
	//글 조회 : 관리자 id. 뷰로 전송 
	@RequestMapping(value = "/checkBoard", method = RequestMethod.GET)
	public String check(
			Model model, @RequestParam int numberId) throws Exception{
		boardDao = new BoardDao();
		Board board = boardDao.check(numberId);
		model.addAttribute("board", board);
		model.addAttribute("managerId", new MemberController().managerId());
		return "board/check";
	}
	
	
	//글 삭제 - 세션 아이디가 일치한지 비교한 후 권한 설정.
	@RequestMapping(value = "/deleteBoard", method = RequestMethod.POST)
	public String delete(
			Model model, Board board,
			HttpSession session) throws Exception{
		
		boardDao = new BoardDao();
		String accessId = session.getAttribute("accessId").toString();
		
		//권한 부여 : 관리자 모드로 접속한다면.
		if(new MemberController().managerId().equals(accessId)){
			/** 세션 id를 선택한 글의 id로 설정 
			  * 세션 id와 선택한 글 id와 계속 일치 */
			accessId = board.getEmail();
		}
		boardDao.delete(board.getNumberId(), accessId);
		BoardList(model, START_DEFAULT_PAGE);
		return "board/list";
	}
	
	
	//글 수정 - 세션 아이디가 일치한지 비교한 후 권한 설정. 
	@RequestMapping(value = "/updateBoard", method = RequestMethod.POST)
	public String update(
			Model model, Board board,
			HttpSession session) throws Exception{
		
		boardDao = new BoardDao();
		String accessId = session.getAttribute("accessId").toString();
		
		//권한 부여 : 관리자 모드로 접속한다면.
		if(new MemberController().managerId().equals(accessId)){
			/** 세션 id를 선택한 글의 id로 설정 
			  * 세션 id와 선택한 글 id와 계속 일치 */
			accessId = board.getEmail();
		}
		boardDao.update(board, accessId);
		BoardList(model, START_DEFAULT_PAGE);
		return "board/list";
	}
	
	
	
	
	

	
	
}
