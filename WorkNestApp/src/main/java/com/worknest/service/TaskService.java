package com.worknest.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worknest.dao.TaskDAO;
import com.worknest.dao.UserDAO;
import com.worknest.model.Task;
import com.worknest.model.User;

@Service
@Transactional
public class TaskService {
    @Autowired
    private TaskDAO taskDAO;
    @Autowired
    private UserDAO userDAO;

    public void allocate(String title, String description, Date start, Date due, List<User> users){
        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setStartDate(start);
        t.setDueDate(due);
        t.setStatus("PENDING");
        t.setUsers(users);
        taskDAO.save(t);
    }
    
    public void reassign(int taskId, User newUser){
        Task t = taskDAO.findById(taskId);
        if(t != null){
            List<User> assigned = t.getUsers();
            assigned.add(newUser);
            t.setUsers(assigned);
            taskDAO.save(t);
        }
    }
    
    



    // Update task ka status
    public void updateStatus(int taskId, String status){
        Task t = taskDAO.findById(taskId);
        if(t != null){
            t.setStatus(status);
            taskDAO.save(t);
        }
    }

    public void forwardTask(int taskId, int newUserId){
        Task t = taskDAO.findById(taskId);
        if(t != null){
            User u = userDAO.findById(newUserId);
            if(!t.getUsers().contains(u)){
                t.getUsers().add(u); // naye user ko bhi same task me add kar do
                taskDAO.save(t);
            }
        }
    }
    
    public void delete(int id){
        Task t = taskDAO.findById(id);
        if(t != null){
            taskDAO.delete(t);
        }
    }

    public void update(int id, String title, String description, String status, Date start, Date due, User user){
        Task t = taskDAO.findById(id);
        if(t != null){
            t.setTitle(title);
            t.setDescription(description);
            t.setStatus(status);
            t.setStartDate(start);
            t.setDueDate(due);
//            t.setUsers(user);
            taskDAO.save(t);
        }
    }
    
    

    public Task byId(int id){ return taskDAO.findById(id); }
    public List<Task> all(){ return taskDAO.findAll(); }
    public List<Task> byUser(User u){
        return taskDAO.findByUser(u);
    }

    public void save(Task task) {
        if (task.getId() == 0) {
            // new task
            taskDAO.save(task);
        } else {
            // existing task → update
            Task existing = taskDAO.findById(task.getId());
            if(existing != null){
                existing.setTitle(task.getTitle());
                existing.setDescription(task.getDescription());
                existing.setStatus(task.getStatus());
                existing.setStartDate(task.getStartDate());
                existing.setDueDate(task.getDueDate());
                existing.setUsers(task.getUsers());
                taskDAO.save(existing);
            }
        }
    }

	
	
}