package com.worknest.dao;

import com.worknest.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void save(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    public User findByEmail(String email) {
        Query<User> q = sessionFactory.getCurrentSession()
                .createQuery("from User where email=:email", User.class);
        q.setParameter("email", email);
        return q.uniqueResult();
    }

    public List<User> findAll(){
        return sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
    }

    public User findById(int id){
        return sessionFactory.getCurrentSession().get(User.class, id);
    }
    
    public void delete(User user){
        sessionFactory.getCurrentSession().delete(user);
    }

}