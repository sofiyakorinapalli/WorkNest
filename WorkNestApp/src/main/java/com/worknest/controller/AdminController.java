package com.worknest.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.worknest.model.Comment;
import com.worknest.model.Task;
import com.worknest.model.User;
import com.worknest.service.CommentService;
import com.worknest.service.TaskService;
import com.worknest.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private CommentService commentService;

    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model){
        User u = (User) session.getAttribute("user");
        if(u == null || !"ADMIN".equalsIgnoreCase(u.getRole())){
            return "redirect:/login";
        }

        List<Task> allTasks = taskService.all();
        model.addAttribute("users", userService.all());
        model.addAttribute("tasks", allTasks);

        // map each task to its comments
        Map<Integer, List<Comment>> taskComments = new HashMap<>();
        for(Task t : allTasks){
            taskComments.put(t.getId(), commentService.forTask(t));
        }
        model.addAttribute("taskComments", taskComments);

        // ✅ count tasks by status
        long pendingCount = allTasks.stream().filter(t -> "PENDING".equalsIgnoreCase(t.getStatus())).count();
        long inProgressCount = allTasks.stream().filter(t -> "IN_PROGRESS".equalsIgnoreCase(t.getStatus())).count();
        long completedCount = allTasks.stream().filter(t -> "COMPLETED".equalsIgnoreCase(t.getStatus())).count();
        long delayedCount = allTasks.stream().filter(t -> "DELAYED".equalsIgnoreCase(t.getStatus())).count();

        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("inProgressCount", inProgressCount);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("delayedCount", delayedCount);

        return "admin-dashboard";
    }

    
    
    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam int userId) {
        userService.delete(userId);
        return "redirect:/admin/dashboard";
    }
    
    
    @PostMapping("/tasks/delete")
    public String deleteTask(@RequestParam int taskId) {
        taskService.delete(taskId);
        return "redirect:/admin/dashboard";
    }
    
    
    @GetMapping("/tasks/edit")
    public String editTaskPage(@RequestParam int taskId, Model model) {
        Task task = taskService.byId(taskId);
        model.addAttribute("task", task);
        model.addAttribute("users", userService.all());
        return "edit-task";
    }
    
    

    @PostMapping("/tasks/edit")
    public String editTask(@RequestParam int id,
                           @RequestParam String title,
                           @RequestParam String description,
                           @RequestParam String status,
                           @RequestParam(required=false) String startDate,
                           @RequestParam(required=false) String dueDate,
                           @RequestParam(value="userIds", required=false) List<Integer> userIds) {

        Date start = (startDate==null || startDate.isEmpty()) ? null : Date.valueOf(startDate);
        Date due = (dueDate==null || dueDate.isEmpty()) ? null : Date.valueOf(dueDate);

        // get existing task
        Task task = taskService.byId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setStartDate(start);
        task.setDueDate(due);

        if (userIds != null && !userIds.isEmpty()) {
            List<User> selectedUsers = userIds.stream()
                    .map(uid -> userService.byId(uid))
                    .toList();
            task.setUsers(selectedUsers);
        } else {
            task.setUsers(null); 
        }

        taskService.save(task); 
        return "redirect:/admin/dashboard";
    }



    @PostMapping("/tasks/allocate")
    public String allocate(@RequestParam String taskCode,
                           @RequestParam String title,
                           @RequestParam(required=false) String description,
                           @RequestParam(required=false) String startDate,
                           @RequestParam(required=false) String dueDate,
                           @RequestParam List<Integer> userIds) {
        Date start = (startDate==null || startDate.isEmpty()) ? null : Date.valueOf(startDate);
        Date due = (dueDate==null || dueDate.isEmpty()) ? null : Date.valueOf(dueDate);
        List<User> users = userIds.stream().map(id -> userService.byId(id)).toList();

        Task t = new Task();
        t.setTaskCode(taskCode); // <-- manual ID
        t.setTitle(title);
        t.setDescription(description);
        t.setStartDate(start);
        t.setDueDate(due);
        t.setStatus("PENDING");
        t.setUsers(users);

        taskService.save(t);
        return "redirect:/admin/dashboard";
    }

    
    

}