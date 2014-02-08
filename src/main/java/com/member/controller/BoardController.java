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
	private final String MANAGERID = "������";
	private final int START_DEFAULT_PAGE = 0; //�ּ� ���� ������ ���� [0���� ����] 
	private final int WRITING_MAX_COUNT = 10;   //�� �������� �� �� 
	private final int PAGE_MAX_COUNT=5;   // ��ü ������ ��ȯ �� 
	
	@Inject
	private BoardDao boardDao;
	
	//������ ���� : ���̵� ������ ���� ������ ���� ���� ����. 
	public String managerSet(String accessId, String email){
		if(MANAGERID.equals(accessId)){
			accessId = email;
		}
		return accessId;  
	}
	
	//�۾���
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
	//�� ��ȸ : ������ id. ��� ���� 
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String check(
			Model model, @RequestParam int id){
		Board board = boardDao.check(id);
		model.addAttribute("board", board);
		model.addAttribute("managerId", MANAGERID);
		return "board/check";
	}
	
	//�� ���� 
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			Model model, HttpSession session, Board board){
		
		String accessId = session.getAttribute("accessId").toString();
		
		//������ ���ӽ� : ���� �ο� �߰�
		accessId = managerSet(accessId, board.getEmail());
		
		boardDao.update(board, accessId);
		list(model, START_DEFAULT_PAGE);
		return "board/list";
	}
	//�� ����
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(
			Model model, HttpSession session, Board board){
		String accessId = session.getAttribute("accessId").toString();
		
		//������ ���ӽ� : ���� �ο� �߰�
		accessId = managerSet(accessId, board.getEmail());
				
		boardDao.delete(board.getId(), accessId);
		list(model, START_DEFAULT_PAGE);
		return "board/list";
	}
	
	
	//�Խ��� ����Ʈ  (����¡ó�� ���� )
	public void list(Model model, int pageNum){
		
		int writeTotalCount = boardDao.totalPage(); //��ü �� ����
		int pageTotalCount = (writeTotalCount / WRITING_MAX_COUNT); //��ü ������ ��� 
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
		model.addAttribute("pageNum", pageNum); //������ �۹�ȣ ����
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("pageMaxCount", PAGE_MAX_COUNT);
		
	}
	//������ ���� 
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam int pageNum, Model model){
		//limit�� 0���� �����ϹǷ� ������ 1���� 0���� ������ֱ� ���� 1�� ��. 
		list(model, pageNum-1);
		return "board/list";
	}
	

	
	
}
