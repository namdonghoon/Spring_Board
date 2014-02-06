package com.member.dao;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.member.domain.Member;

@Transactional
@Repository
public class MemberDaoTest {
	
	@Inject   //오토와이어와 일치.
	private SessionFactory sessionFactory;

	
	public Member login(String email, String pass) {
	    Session session = this.sessionFactory.getCurrentSession();
	    String sql = "from Member where email = ? and pass = ?";
	    Query query = session.createQuery(sql);
	    query.setParameter(0, email);
	    query.setParameter(1, pass);
	    
	    Member member = (Member) query.uniqueResult();
	    return member; 
	}  
	
	public void save(Member member) { 
		this.sessionFactory.getCurrentSession().save(member);
	} 
	
}
