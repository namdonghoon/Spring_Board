package com.member.dao;

import java.sql.Timestamp;
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
public class BoardDaoTest {
	
	
	@Inject
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	//�� ����   
	public void save(Board board) {
		board.setDate(new Timestamp(System.currentTimeMillis()));
        this.sessionFactory.getCurrentSession().save(board);
    }
	
	//�� ��ȸ : id ��ġ 
	public Board check(int id){
		Board board = (Board) this.sessionFactory.getCurrentSession().load(Board.class, id);
		return board;
	}
	//�� ��ȸ : id, email ��ġ 
	public Board check(int id, String email){
		Session session = this.sessionFactory.getCurrentSession();
	    String sql = "from Board where id = ? and email = ?";
	    Query query = session.createQuery(sql);
	    query.setParameter(0, id);
	    query.setParameter(1, email);
		return (Board) query.uniqueResult();
	}
	
	//�� ���� 
	public void update(Board board, String accessId){		
		Session session = this.sessionFactory.getCurrentSession();
	    String sql = "update Board set title=?, content=? where id=? and email=?";
	    Query query = session.createQuery(sql);
	    query.setString(0, board.getTitle());
	    query.setString(1, board.getContent());
	    query.setInteger(2, board.getId());
	    query.setString(3, accessId);
	    query.executeUpdate();
	}
		
    //�� ���� : Ŀ���� ��ȸ + ���̹�����Ʈ ���� ���. 
    public void delete(int id, String accessId) {
        Board board = check(id, accessId);
        if(board != null){
        	this.sessionFactory.getCurrentSession().delete(board);
        }
    }
    
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
