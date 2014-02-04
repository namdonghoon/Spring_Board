package com.member.domain;

public class Member {
	private String member_Email;
	private String member_name;
	private String member_pass;
	private String member_conpass;
	
	public Member(){}

	public Member(String member_Email, String member_name, String member_pass,
			String member_conpass) {
		super();
		this.member_Email = member_Email;
		this.member_name = member_name;
		this.member_pass = member_pass;
		this.member_conpass = member_conpass;
	}

	public String getMember_Email() {
		return member_Email;
	}
	public void setMember_Email(String member_Email) {
		this.member_Email = member_Email;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_pass() {
		return member_pass;
	}
	public void setMember_pass(String member_pass) {
		this.member_pass = member_pass;
	}
	public String getMember_conpass() {
		return member_conpass;
	}
	public void setMember_conpass(String member_conpass) {
		this.member_conpass = member_conpass;
	}
	
	
	
	
}
