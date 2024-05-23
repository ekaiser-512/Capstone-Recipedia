package org.example.capstonebackend.controller;

import org.apache.coyote.Response;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IUserRepository;
import org.example.capstonebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    IUserRepository userRepository;

//CREATE
    //create user
    @PostMapping("/users") //todo why is DOB null?
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

//READ
    //get user by id
    @GetMapping("users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
            try {
                User user = userService.getUserById(id);
                return ResponseEntity.ok(user);
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
    }

    //get user by email
    @GetMapping("/users/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users;
    }

//UPDATE
    //update User
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updatedUser(@PathVariable int id, @RequestBody User user) throws Exception {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


//DELETE
    //delete user
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }


}
