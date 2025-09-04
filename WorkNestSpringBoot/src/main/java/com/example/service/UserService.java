package com.example.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.User;
import com.example.repository.UserRepository;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register new user
    public void register(User user) {
        userRepository.save(user);
    }

    // Login check
    public User login(String email, String password) {
        User u = userRepository.findByEmail(email);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }
    public User save(User user) {
        return userRepository.save(user);
    }

    // Fetch all users
    public List<User> all() {
        return userRepository.findAll();
    }

    // Fetch user by ID
    public User byId(int id) {
        return userRepository.findById(id).orElse(null);
    }

    // Delete user by ID
    public void delete(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }
}
