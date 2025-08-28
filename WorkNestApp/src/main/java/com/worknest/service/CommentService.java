package com.worknest.service;

import com.worknest.dao.CommentDAO;
import com.worknest.model.Comment;
import com.worknest.model.Task;
import com.worknest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    public void add(Task task, User user, String content){
        Comment c = new Comment();
        c.setTask(task);
        c.setUser(user);
        c.setContent(content);
        commentDAO.save(c);
    }

    public List<Comment> forTask(Task task){
        return commentDAO.findByTask(task);
    }
}