package com.example.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Comment;
import com.example.model.Task;
import com.example.model.User;
import com.example.service.CommentService;
import com.example.service.TaskService;
import com.example.service.UserService;

import jakarta.servlet.http.HttpSession;

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

 // Show edit user page
    @GetMapping("/users/edit")
    public String editUserPage(@RequestParam int userId, Model model) {
        User user = userService.byId(userId);
        if(user == null) return "redirect:/admin/dashboard?error=UserNotFound";

        model.addAttribute("user", user);
        return "edit-user";
    }

    // Handle edit user form submission
    @PostMapping("/users/edit")
    public String editUser(@RequestParam int id,
                           @RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String role) {

        User user = userService.byId(id);
        if(user == null) return "redirect:/admin/dashboard?error=UserNotFound";

        user.setName(name);
        user.setEmail(email);
        user.setRole(role.toUpperCase()); // ensure role consistency

        userService.save(user);
        return "redirect:/admin/dashboard?success=UserUpdated";
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
                           @RequestParam(value="userIds", required=false) String[] userIds) {

        Task task = taskService.byId(id);
        if(task == null) return "redirect:/admin/dashboard?error=TaskNotFound";

        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);

        try {
            task.setStartDate((startDate != null && !startDate.isEmpty()) ? Date.valueOf(startDate) : null);
            task.setDueDate((dueDate != null && !dueDate.isEmpty()) ? Date.valueOf(dueDate) : null);
        } catch (IllegalArgumentException e) {
            task.setStartDate(null);
            task.setDueDate(null);
        }

        // Update users safely
        List<User> selectedUsers = new ArrayList<>();
        if(userIds != null){
            for(String uidStr : userIds){
                try {
                    User u = userService.byId(Integer.parseInt(uidStr));
                    if(u != null) selectedUsers.add(u);
                } catch(NumberFormatException ignored){}
            }
        }
        task.getUsers().clear();
        task.getUsers().addAll(selectedUsers);

        taskService.save(task);
        return "redirect:/admin/dashboard?success=TaskUpdated";
    }




    @PostMapping("/tasks/allocate")
    public String allocate(
                           @RequestParam String title,
                           @RequestParam(required=false) String description,
                           @RequestParam(required=false) String startDate,
                           @RequestParam(required=false) String dueDate,
                           @RequestParam List<Integer> userIds) {
        Date start = (startDate==null || startDate.isEmpty()) ? null : Date.valueOf(startDate);
        Date due = (dueDate==null || dueDate.isEmpty()) ? null : Date.valueOf(dueDate);
        List<User> users = userIds.stream().map(id -> userService.byId(id)).toList();

        Task t = new Task();
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
