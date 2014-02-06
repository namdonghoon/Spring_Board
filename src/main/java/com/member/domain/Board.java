package com.member.domain;

import java.sql.Timestamp;

public class Board {
	private int id;
	private String email;
	private String title;
	private String content;
	private Timestamp date;
	
	public Board(){}
	
	public Board(int id, String email, String title, String content,
			Timestamp date) {
		super();
		this.id = id;
		this.email = email;
		this.title = title;
		this.content = content;
		this.date = date;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	
	
	
	
	
}
