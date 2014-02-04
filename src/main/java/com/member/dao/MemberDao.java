package com.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.member.db.DBConnect;
import com.member.domain.Board;
import com.member.domain.Member;

public class MemberDao {
	private Connection conn;
	
	public MemberDao(){
		conn = new DBConnect().getConn();
	}
	
	//ȸ������ 
	public void insertMember(Member member) throws SQLException {
		PreparedStatement  pstmt = null;
		String sql = "insert into MEMBER values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMember_Email());
			pstmt.setString(2, member.getMember_name());
			pstmt.setString(3, member.getMember_pass());
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	//ȸ������ ȹ��
	public Member getMember(String id, String pass) throws SQLException {
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		Member member = new Member();
		
		String sql = "select * from MEMBER where member_Email=? and member_pass=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);

			rs = pstmt.executeQuery(); // ������� ������

			if (rs.next()) {
				member.setMember_Email(rs.getString(1));
				member.setMember_name(rs.getString(2));
				member.setMember_pass(rs.getString(3));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if (pstmt != null) {
				pstmt.close();
			}		
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}	
		}
		return member;
	}
	//��ȣ üũ
	public Member EmailCheck(String id) throws SQLException {
		ResultSet rs = null;
		PreparedStatement  pstmt = null;
		Member member = new Member();
		
		try {
			String sql = "select * from member where member_Email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member.setMember_Email(rs.getString(1));
				member.setMember_name(rs.getString(2));
				member.setMember_pass(rs.getString(3));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}			
			if (rs != null) {
				rs.close();
			}
		}
		return member;
	}


	//�۾���
	public void insertBoard(Board board)throws SQLException {
		PreparedStatement  pstmt = null;
		String sql = "insert into Board values(?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getBoard_num());
			pstmt.setString(2, board.getBoard_Email());
			pstmt.setString(3, board.getBoard_title());
			pstmt.setString(4, board.getBoard_content());
			pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	
	
	
	//��ü�� ���� 
	public int getBoardCount(){
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(board_num) from Board";
		try {
			//�ٸ� Connection ��ü ���� : �ѹ��� �׼ǿ� �ι��� DAO�� �����ϱ� ����  
			Connection conn = new DBConnect().getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}
	
	//��ü �� ��� ����¡ ó�� 
	public List<Board> getBoard_list(int wrStarNum, int wrMaxCount) throws SQLException{
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		Board board = null;
		List<Board> list = new ArrayList<Board>();
		String sql = "select * from board order by board_num desc limit ?, ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, wrStarNum);  //���� ��  
			pstmt.setInt(2, wrMaxCount); //�� �������� ǥ�� �� �� 
			rs = pstmt.executeQuery(); // ������� ������
			
			while(rs.next()){
				board = new Board();
				board.setBoard_num(rs.getInt(1));
				board.setBoard_Email(rs.getString(2));
				board.setBoard_title(rs.getString(3));
				board.setBoard_content(rs.getString(4));
				board.setBoard_date(rs.getTimestamp(5));
				list.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}	
		}
		
		return list;
		
	}
	
	//�� �������� 
	public Board getBoard(int board_num) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board board =  new Board();
		
		String sql = "select * from Board where board_num=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);

			rs = pstmt.executeQuery(); // ������� ������

			if (rs.next()) {
				board.setBoard_num(rs.getInt(1));
				board.setBoard_Email(rs.getString(2));
				board.setBoard_title(rs.getString(3));
				board.setBoard_content(rs.getString(4));
				board.setBoard_date(rs.getTimestamp(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return board;
	}

	
	//�� ���� 
	public void deleteBoard(int board_num, String Email_id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql = "delete from Board where board_num=? and board_Email=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.setString(2, Email_id);

			pstmt.executeUpdate();

		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	
	//�� ���� 
	public void updateBoard(Board board, String Email_id) throws Exception{
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("update Board ");
		sql.append("set board_title=?, board_content=? ");
		sql.append("where board_num=? and board_Email=?");
		
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, board.getBoard_title());
			pstmt.setString(2, board.getBoard_content());
			pstmt.setInt(3, board.getBoard_num());
			pstmt.setString(4, Email_id);
			
			pstmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	//���̵� �ߺ� üũ 
	public Member id_check(String Email_id) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from Member where member_Email=?";
		Member member = new Member();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Email_id);

			rs = pstmt.executeQuery(); // ������� ������

			if (rs.next()) {
				member.setMember_Email(rs.getString(1));
				member.setMember_name(rs.getString(2));
				member.setMember_pass(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}if(rs != null){
				rs.close();
			}
		}
		return member;
	} 

}
