package com.member.domain;

public class Member {
	private String email;
	private String name;
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
