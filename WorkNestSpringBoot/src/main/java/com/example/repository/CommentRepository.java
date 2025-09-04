package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Comment;
import com.example.model.Task;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByTask(Task task);
}
