package com.example.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Task;
import com.example.model.User;
import com.example.repository.TaskRepository;
import com.example.repository.UserRepository;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // Allocate a new task to multiple users
    public void allocate(String title, String description, Date start, Date due, List<User> users) {
        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setStartDate(start);
        t.setDueDate(due);
        t.setStatus("PENDING");
        t.setUsers(users);
        taskRepository.save(t);
    }

    // Reassign task to a new user
    public void reassign(int taskId, User newUser) {
        Task t = taskRepository.findById(taskId).orElse(null);
        if (t != null) {
            List<User> assigned = t.getUsers();
            assigned.add(newUser);
            t.setUsers(assigned);
            taskRepository.save(t);
        }
    }

    // Update task status
    public void updateStatus(int taskId, String status) {
        Task t = taskRepository.findById(taskId).orElse(null);
        if (t != null) {
            t.setStatus(status);
            taskRepository.save(t);
        }
    }

    // Forward task to another user
    public void forwardTask(int taskId, int newUserId) {
        Task t = taskRepository.findById(taskId).orElse(null);
        if (t != null) {
            User u = userRepository.findById(newUserId).orElse(null);
            if (u != null && !t.getUsers().contains(u)) {
                t.getUsers().add(u);
                taskRepository.save(t);
            }
        }
    }

    // Delete task
    public void delete(int id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        }
    }

    // Update task details
    public void update(int id, String title, String description, String status, Date start, Date due, User user) {
        Task t = taskRepository.findById(id).orElse(null);
        if (t != null) {
            t.setTitle(title);
            t.setDescription(description);
            t.setStatus(status);
            t.setStartDate(start);
            t.setDueDate(due);
            taskRepository.save(t);
        }
    }

    // Get task by ID
    public Task byId(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    // Get all tasks
    public List<Task> all() {
        return taskRepository.findAll();
    }

    // Get tasks by user
    public List<Task> byUser(User u) {
        return taskRepository.findByUsers(u);
    }

    // Save or update task
    public void save(Task task) {
        if (task.getId() == 0) {
            taskRepository.save(task);
        } else {
            Task existing = taskRepository.findById(task.getId()).orElse(null);
            if (existing != null) {
                existing.setTitle(task.getTitle());
                existing.setDescription(task.getDescription());
                existing.setStatus(task.getStatus());
                existing.setStartDate(task.getStartDate());
                existing.setDueDate(task.getDueDate());
                existing.setUsers(task.getUsers());
                taskRepository.save(existing);
            }
        }
    }
    
//    @Transactional
    public void updatetaskStatus(int taskId, String status) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            task.setStatus(status);
            taskRepository.save(task);
        }
    }

    public List<Task> tasksForUser(int userId) {
        return taskRepository.findByUsers_Id(userId);
    }


}
