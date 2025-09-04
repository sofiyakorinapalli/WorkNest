package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Comment;
import com.example.model.Task;
import com.example.model.User;
import com.example.repository.CommentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public void add(Task task, User user, String content) {
        Comment c = new Comment();
        c.setTask(task);
        c.setUser(user);
        c.setContent(content);
        commentRepository.save(c);
    }

    public List<Comment> forTask(Task task) {
        return commentRepository.findByTask(task);
    }
}
