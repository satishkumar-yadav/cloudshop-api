package com.sky.cloudshop_api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service 
public class UserService {
     private List<User> userList = new ArrayList<>();
     private Long nextId = 1L;
    
    public List<User> fetchAllUsers() {
        return userList;
    }

    public User fetchUser(Long id) {
        for (User user : userList) 
            if(user.getId().equals(id)) return user;
        return null;
    }

    public void addUser(User user) {
        user.setId(nextId++);
        userList.add(user);
    }
}
