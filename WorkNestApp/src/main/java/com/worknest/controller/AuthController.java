package com.worknest.controller;

import com.worknest.model.User;
import com.worknest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(){ return "login"; }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session,
                          Model model){
        User u = userService.login(email, password);
        if(u == null){
            model.addAttribute("error","Invalid credentials");
            return "login";
        }
        session.setAttribute("user", u);
        if("ADMIN".equalsIgnoreCase(u.getRole())) return "redirect:/admin/dashboard";
        return "redirect:/user/tasks";
    }

    @GetMapping("/register")
    public String registerPage(){ return "register"; }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute User user){
        if(user.getRole() == null || user.getRole().trim().isEmpty()) user.setRole("USER");
        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}