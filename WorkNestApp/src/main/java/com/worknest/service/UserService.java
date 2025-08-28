
package com.worknest.service;

import com.worknest.dao.UserDAO;
import com.worknest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public void register(User u){ userDAO.save(u); }

    public User login(String email, String password){
        User u = userDAO.findByEmail(email);
        if(u != null && u.getPassword().equals(password)) return u;
        return null;
    }

    public List<User> all(){ return userDAO.findAll(); }

    public User byId(int id){ return userDAO.findById(id); }
    
    public void delete(int id){
        User u = userDAO.findById(id);
        if(u != null){
            userDAO.delete(u);
        }
    }

	


}
