package com.member.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="board")
public class Board {
	@Id
	@Column(name="id")
	@GeneratedValue   //주키값 id를 자동으로 생성 
	private int id;
	
	@Column(name="email")
	private String email;
	
	@Column(name="title")
	private String title;
	
	@Column(name="content")
	private String content;
	
	@Column(name="date")
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
