package com.member.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.member.domain.Board;

@Transactional
@Repository
public class BoardDaoTest {
	@Inject
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	public void save(Board board) {
        this.sessionFactory.getCurrentSession().save(board);
    }
	
//	@SuppressWarnings("unchecked")
    public List<Board> listContact() {
        System.out.println("SessionFactory is " + this.sessionFactory);
        return this.sessionFactory.getCurrentSession().createQuery("FROM Board").list();
    }
    
    
    public void removeContact(int id) {
        Board board = (Board) this.sessionFactory.getCurrentSession().load(Board.class, id);

        if ( null != board ) {
            this.sessionFactory.getCurrentSession().delete(board);
        }
    }
}
