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
			pstmt.setString(1, member.getEmailId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPass());
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
		
		String sql = "select * from MEMBER where EmailId=? and pass=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);

			rs = pstmt.executeQuery(); // ������� ������

			if (rs.next()) {
				member.setEmailId(rs.getString(1));
				member.setName(rs.getString(2));
				member.setPass(rs.getString(3));
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
	public Member passCheck(String id) throws SQLException {
		ResultSet rs = null;
		PreparedStatement  pstmt = null;
		Member member = new Member();
		
		try {
			String sql = "select * from member where EmailId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member.setEmailId(rs.getString(1));
				member.setName(rs.getString(2));
				member.setPass(rs.getString(3));
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


	
	
	//���̵� �ߺ� üũ 
	public Member idCheck(String id) throws Exception{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from Member where EmailId=?";
		Member member = new Member();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery(); // ������� ������

			if (rs.next()) {
				member.setEmailId(rs.getString(1));
				member.setName(rs.getString(2));
				member.setPass(rs.getString(3));
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
