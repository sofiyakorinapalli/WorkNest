package com.example.controller;

import java.sql.Date;
import java.util.List;

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

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    // Dashboard page showing only tasks assigned to this user
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User u = (User) session.getAttribute("user");
        if (u == null || !"USER".equalsIgnoreCase(u.getRole())) {
            return "redirect:/login";
        }

        List<Task> assignedTasks = taskService.tasksForUser(u.getId());
        model.addAttribute("tasks", assignedTasks);

        // Map each task to its comments
        assignedTasks.forEach(t -> {
            List<Comment> comments = commentService.forTask(t);
            model.addAttribute("comments_" + t.getId(), comments);
        });

        return "user-dashboard";
    }

    // Update task status
    @PostMapping("/tasks/updateStatus")
    public String updateStatus(@RequestParam int taskId, @RequestParam String status, HttpSession session) {
        User u = (User) session.getAttribute("user");
        if (u == null) return "redirect:/login";

        Task task = taskService.byId(taskId);
        if (task.getUsers().contains(u)) {
            taskService.updatetaskStatus(taskId, status);
        }

        return "redirect:/user/dashboard";
    }

    // Add comment to a task
//    @PostMapping("/tasks/addComment")
//    public String addComment(@RequestParam int taskId, @RequestParam String content, HttpSession session) {
//        User u = (User) session.getAttribute("user");
//        if (u == null) return "redirect:/login";
//
//        Task task = taskService.byId(taskId);
//        if (task.getUsers().contains(u)) {
//            Comment c = new Comment();
//            c.setContent(content);
//            c.setTask(task);
//            c.setUser(u);
//            c.setCreatedAt(new Date(System.currentTimeMillis()));
//            commentService.save(c);
//        }
//
//        return "redirect:/user/dashboard";
//    }
}
