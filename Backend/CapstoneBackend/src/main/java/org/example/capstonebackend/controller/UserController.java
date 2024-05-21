package org.example.capstonebackend.controller;

import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IUserRepository;
import org.example.capstonebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    IUserRepository userRepository;

//CREATE
    //create user
    @PostMapping("/users")
    public ResponseEntity<User> addUser (@RequestBody User user) throws Exception {
        try {
            //Trying to add post
            User addedUser = userService.addUser(user);
            //if successful, return OK (200) response with the added post
            return ResponseEntity.ok(addedUser);
        } catch (Exception e) {
            // If an exception occurs during adding the post, return Bad Request (400) response
            return ResponseEntity.badRequest().build();
        }
    }

}
