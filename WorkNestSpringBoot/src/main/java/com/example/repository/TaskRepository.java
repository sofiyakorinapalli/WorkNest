package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Task;
import com.example.model.User;

public interface TaskRepository extends JpaRepository<Task, Integer>{
	List<Task> findByUsers(User user);
	List<Task> findByUsers_Id(int userId);
}
