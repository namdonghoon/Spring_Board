package com.member.dao;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.member.domain.Board;
import com.member.domain.Member;

@Transactional
@Repository
public class MemberDao {
	
	@Inject   //오토와이어와 일치.
	private SessionFactory sessionFactory;
 

	//로그인 : 아이디
	public Member idCheck(String email) { //load 함수의 찾는 결과가 없을때 null이 아닌. 에러발생.
		 Session session = this.sessionFactory.getCurrentSession();
	    String sql = "from Member where email = ?";
	    Query query = session.createQuery(sql);
	    query.setParameter(0, email);
	   
	    Member member = (Member) query.uniqueResult();
	    return member; 
	}  
	//로그인 : 암호 
	public Member passCheck(String email, String pass) {
	    Session session = this.sessionFactory.getCurrentSession();
	    String sql = "from Member where email = ? and pass = ?";
	    Query query = session.createQuery(sql);
	    query.setParameter(0, email);
	    query.setParameter(1, pass);
	   
	    Member member = (Member) query.uniqueResult();
	    return member; 
	}  
	//회원가입 
	public void save(Member member) { 
		this.sessionFactory.getCurrentSession().save(member);
	}
	
	
	
	
	
	
	
	
	
	//Board 외부영역 글목록 갱신하기위해 필요 
	//전체 게시글 갯수 
    public int totalPage() {
        return this.sessionFactory.getCurrentSession().createQuery("FROM Board").list().size();
    }
    //페이징 처리 
    @SuppressWarnings("unchecked")
	public List<Board> list(int firstPage, int maxPage){
		Session session = this.sessionFactory.getCurrentSession();
	    Criteria crit = session.createCriteria(Board.class);
	    crit.addOrder( Order.desc("id") );
	    crit.setFirstResult(firstPage);
	    crit.setMaxResults(maxPage);
	    List<Board> list =  crit.list();
	    
	    
		return list;
	}
	
	
	
}
