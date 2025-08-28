package com.worknest.dao;

import com.worknest.model.Comment;
import com.worknest.model.Task;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void save(Comment c){
        sessionFactory.getCurrentSession().saveOrUpdate(c);
    }

    public List<Comment> findByTask(Task task){
        Query<Comment> q = sessionFactory.getCurrentSession()
                .createQuery("from Comment where task=:t order by createdAt desc", Comment.class);
        q.setParameter("t", task);
        return q.list();
    }
}