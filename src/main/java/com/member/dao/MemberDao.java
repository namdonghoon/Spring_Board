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
	
	@Inject   //������̾�� ��ġ.
	private SessionFactory sessionFactory;
 

	//�α��� : ���̵�
	public Member idCheck(String email) { //load �Լ��� ã�� ����� ������ null�� �ƴ�. �����߻�.
		 Session session = this.sessionFactory.getCurrentSession();
	    String sql = "from Member where email = ?";
	    Query query = session.createQuery(sql);
	    query.setParameter(0, email);
	   
	    Member member = (Member) query.uniqueResult();
	    return member; 
	}  
	//�α��� : ��ȣ 
	public Member passCheck(String email, String pass) {
	    Session session = this.sessionFactory.getCurrentSession();
	    String sql = "from Member where email = ? and pass = ?";
	    Query query = session.createQuery(sql);
	    query.setParameter(0, email);
	    query.setParameter(1, pass);
	   
	    Member member = (Member) query.uniqueResult();
	    return member; 
	}  
	//ȸ������ 
	public void save(Member member) { 
		this.sessionFactory.getCurrentSession().save(member);
	}
	
	
	
	
	
	
	
	
	
	//Board �ܺο��� �۸�� �����ϱ����� �ʿ� 
	//��ü �Խñ� ���� 
    public int totalPage() {
        return this.sessionFactory.getCurrentSession().createQuery("FROM Board").list().size();
    }
    //����¡ ó�� 
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
