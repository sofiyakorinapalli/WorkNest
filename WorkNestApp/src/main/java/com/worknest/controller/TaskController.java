package com.worknest.controller;

import com.worknest.model.Task;
import com.worknest.model.User;
import com.worknest.service.CommentService;
import com.worknest.service.TaskService;
import com.worknest.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @GetMapping("/tasks")
    public String myTasks(HttpSession session, Model model, 
                          @ModelAttribute("msg") String msg){
        User u = (User) session.getAttribute("user");
        if(u == null){ 
            return "redirect:/login"; 
        }

        // Current user's tasks
        List<Task> tasks = taskService.byUser(u);
        model.addAttribute("tasks", tasks);

        // All non-admin users for reassign dropdown
        List<User> users = userService.all();
        model.addAttribute("users", users);

        // Flash message (agar hai to)
        if(msg != null && !msg.isEmpty()){
            model.addAttribute("msg", msg);
        }

        return "user-tasks";
    }

    @PostMapping("/tasks/reassign")
    public String reassignTask(@RequestParam int taskId, 
                               @RequestParam int newUserId, 
                               HttpSession session,
                               RedirectAttributes redirectAttributes){
        User u = (User) session.getAttribute("user");
        if(u == null) return "redirect:/login";

        User newUser = userService.byId(newUserId);
        taskService.reassign(taskId, newUser);

        redirectAttributes.addFlashAttribute("msg", "✅ Task reassigned successfully to " + newUser.getName() + "!");
        return "redirect:/user/tasks";
    }
    
    @PostMapping("/tasks/forward")
    public String forwardTask(@RequestParam int taskId,
                              @RequestParam int newUserId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        User u = (User) session.getAttribute("user");
        if(u == null) return "redirect:/login";

        taskService.forwardTask(taskId, newUserId);

        redirectAttributes.addFlashAttribute("msg", "✅ Task forwarded successfully!");
        return "redirect:/user/tasks";
    }


    @PostMapping("/tasks/status")
    public String updateStatus(@RequestParam int taskId, @RequestParam String status, HttpSession session){
        User u = (User) session.getAttribute("user");
        if(u == null){ return "redirect:/login"; }
        taskService.updateStatus(taskId, status);
        return "redirect:/user/tasks";
    }

    @PostMapping("/tasks/comment")
    public String addComment(@RequestParam int taskId, @RequestParam String content, HttpSession session){
        User u = (User) session.getAttribute("user");
        if(u == null){ return "redirect:/login"; }
        Task t = taskService.byId(taskId);
        if(t != null){
            commentService.add(t, u, content);
        }
        return "redirect:/user/tasks";
    }
    
//    @GetMapping("/tasks/{taskId}")
//    public String showTaskDetails(@PathVariable Long taskId, Model model) {
//        Task task = taskService.getTaskById(taskId);
//        List<User> users = userService.getAllUsers(); // yahi sirf dikhana hai dropdown me
//
//        model.addAttribute("task", task);
//        model.addAttribute("users", users);
//        return "taskDetails"; // same JSP page
//    }

}