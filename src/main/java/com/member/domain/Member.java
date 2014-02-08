package com.member.domain;

import java.lang.annotation.Documented;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;


@Entity
@Table(name="Member")
public class Member {
	
	@Id
	@NotEmpty(message="이메일을 입력해주세요.")
	@Column(name="email")
	private String email;
	
	@Column(name="name")
	private String name;
	
	@Length(min = 3, max=12, message="3~12 자리수로 입력해주세요.")
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
