package com.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Member")
public class Member {
	
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="name")
	private String name;
	
	@Column(name="pass")
	private String pass;
	
	private String conpass;
	
	public Member(){}

	public Member(String email, String name, String pass, String conpass) {
		super();
		this.email = email;
		this.name = name;
		this.pass = pass;
		this.conpass = conpass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getConpass() {
		return conpass;
	}

	public void setConpass(String conpass) {
		this.conpass = conpass;
	}
	
	
	
	
}
