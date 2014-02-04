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

public class BoardDao {
private Connection conn;
	
	public BoardDao(){
		conn = new DBConnect().getConn();
	}
	
	//글쓰기
		public void save(Board board)throws SQLException {
			PreparedStatement  pstmt = null;
			String sql = "insert into Board values(?,?,?,?,?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, board.getNumberId());
				pstmt.setString(2, board.getEmail());
				pstmt.setString(3, board.getTitle());
				pstmt.setString(4, board.getContent());
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
		
		
		
		
		//전체글 개수 
		public int totalCount(){
			int count = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "select count(numberId) from Board";
			try {
				//다른 Connection 객체 생성 : 한번의 액션에 두번의 DAO가 접근하기 위함  
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
		
		//전체 글 목록 페이징 처리 
		public List<Board> list(int writeStarNum, int writeMaxCount) throws SQLException{
			PreparedStatement  pstmt = null;
			ResultSet rs = null;
			Board board = null;
			List<Board> list = new ArrayList<Board>();
			String sql = "select * from board order by numberId desc limit ?, ?";
			try{
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, writeStarNum);  //시작 글  
				pstmt.setInt(2, writeMaxCount); //한 페이지에 표시 글 수 
				rs = pstmt.executeQuery(); // 결과값을 가져옴
				
				while(rs.next()){
					board = new Board();
					board.setNumberId(rs.getInt(1));
					board.setEmail(rs.getString(2));
					board.setTitle(rs.getString(3));
					board.setContent(rs.getString(4));
					board.setDate(rs.getTimestamp(5));
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
				if(rs != null){
					rs.close();
				}
			}
			
			return list;
			
		}
		
		//글 세부정보 
		public Board check(int numberId) throws Exception{
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Board board =  new Board();
			
			String sql = "select * from Board where numberId=?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, numberId);

				rs = pstmt.executeQuery(); // 결과값을 가져옴

				if (rs.next()) {
					board.setNumberId(rs.getInt(1));
					board.setEmail(rs.getString(2));
					board.setTitle(rs.getString(3));
					board.setContent(rs.getString(4));
					board.setDate(rs.getTimestamp(5));
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

		
		//글 삭제 
		public void delete(int numberId, String accessId) throws SQLException {
			PreparedStatement pstmt = null;
			String sql = "delete from Board where numberId=? and Email=?";

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, numberId);
				pstmt.setString(2, accessId);

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
		
		
		//글 수정 
		public void update(Board board, String accessId) throws Exception{
			PreparedStatement pstmt = null;
			
			StringBuffer sql = new StringBuffer();
			sql.append("update Board ");
			sql.append("set title=?, content=? ");
			sql.append("where numberId=? and email=?");
			
			try {
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, board.getTitle());
				pstmt.setString(2, board.getContent());
				pstmt.setInt(3, board.getNumberId());
				pstmt.setString(4, accessId);
				
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
}
