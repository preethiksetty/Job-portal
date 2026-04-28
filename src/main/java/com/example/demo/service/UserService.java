package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User login(String email, String password) {

        User user = userRepository.findByEmail(email.trim())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password.trim())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
}