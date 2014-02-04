package com.member.domain;

import java.sql.Timestamp;

public class Board {
	private int board_num;
	private String board_Email;
	private String board_title;
	private String board_content;
	private Timestamp board_date;
	
	public Board(){}
	
	
	public Board(int board_num, String board_Email, String board_title,
			String board_content, Timestamp board_date) {
		super();
		this.board_num = board_num;
		this.board_Email = board_Email;
		this.board_title = board_title;
		this.board_content = board_content;
		this.board_date = board_date;
	}


	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getBoard_Email() {
		return board_Email;
	}
	public void setBoard_Email(String board_Email) {
		this.board_Email = board_Email;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public Timestamp getBoard_date() {
		return board_date;
	}
	public void setBoard_date(Timestamp board_date) {
		this.board_date = board_date;
	}
	
	
	
	
	
}
