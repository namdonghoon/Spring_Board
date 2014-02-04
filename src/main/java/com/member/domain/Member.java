package com.member.domain;

public class Member {
	private String emailId;
	private String name;
	private String pass;
	private String conpass;
	
	public Member(){}

	

	public Member(String emailId, String name, String pass, String conpass) {
		super();
		this.emailId = emailId;
		this.name = name;
		this.pass = pass;
		this.conpass = conpass;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
