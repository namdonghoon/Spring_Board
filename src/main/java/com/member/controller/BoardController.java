package com.member.controller;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.member.dao.BoardDao;
import com.member.domain.Board;

// http://localhost:8080/board/list

@Controller
@RequestMapping(value="/board")
public class BoardController {
	private final String MANAGERID = "관리자";
	private final int START_DEFAULT_PAGE = 0; //최소 시작 페이지 설정 [0부터 시작] 
	private final int WRITING_MAX_COUNT = 10;   //한 페이지당 글 수 
	private final int PAGE_MAX_COUNT=5;   // 전체 페이지 전환 수 
	
	@Inject
	private BoardDao boardDao;
	
	//관리자 설정 : 아이디 설정에 따라 관리자 권한 설정 가능. 
	public String managerSet(String accessId, String email){
		if(MANAGERID.equals(accessId)){
			accessId = email;
		}
		return accessId;  
	}
	
	//글쓰기
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save() {
		return "board/save";
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCheck(
			Model model, Board board) {
		boardDao.save(board);
		list(model, START_DEFAULT_PAGE);
		return "board/list";
	}
	//글 조회 : 관리자 id. 뷰로 전송 
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String check(
			Model model, @RequestParam int id){
		Board board = boardDao.check(id);
		model.addAttribute("board", board);
		model.addAttribute("managerId", MANAGERID);
		return "board/check";
	}
	
	//글 수정 
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model, HttpSession session, Board board){
		
		String accessId = session.getAttribute("accessId").toString();
		
		//관리자 접속시 : 권한 부여 추가
		accessId = managerSet(accessId, board.getEmail());
		
		boardDao.update(board, accessId);
		list(model, START_DEFAULT_PAGE);
		return "board/list";
	}
	//글 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(
			Model model, HttpSession session, Board board){
		String accessId = session.getAttribute("accessId").toString();
		
		//관리자 접속시 : 권한 부여 추가
		accessId = managerSet(accessId, board.getEmail());
				
		boardDao.delete(board.getId(), accessId);
		list(model, START_DEFAULT_PAGE);
		return "board/list";
	}
	
	
	//게시판 리스트  (페이징처리 포함 )
	public void list(Model model, int pageNum){
		
		int writeTotalCount = boardDao.totalPage(); //전체 글 개수
		int pageTotalCount = (writeTotalCount / WRITING_MAX_COUNT); //전체 페이지 계산 
		int startPage = (pageNum/PAGE_MAX_COUNT) *PAGE_MAX_COUNT +1; //
	
		int endPage;
		//ex) 1, 1~2, 1~3 ... 1~9
		if(pageTotalCount-(startPage-1) < PAGE_MAX_COUNT){
			endPage = (startPage) + (pageTotalCount % PAGE_MAX_COUNT);
		}else{  //ex) 1~10, 11~20, 21~30
			endPage = (startPage-1) + PAGE_MAX_COUNT;
		}
		
		int writeStarNum = ((pageNum) * WRITING_MAX_COUNT);
		List<Board> list = boardDao.list(writeStarNum, WRITING_MAX_COUNT);
		model.addAttribute("list", list);
		model.addAttribute("pageNum", pageNum); //선택한 글번호 전송
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("pageMaxCount", PAGE_MAX_COUNT);
		
	}
	//페이지 선택 
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam int pageNum, Model model){
		//limit가 0부터 시작하므로 페이지 1에서 0으로 만들어주기 위해 1을 뺌. 
		list(model, pageNum-1);
		return "board/list";
	}
	

	
	
}
