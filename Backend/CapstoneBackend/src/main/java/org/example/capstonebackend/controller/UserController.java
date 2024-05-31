package org.example.capstonebackend.controller;

import org.apache.coyote.Response;
import org.example.capstonebackend.DTO.CommonResponseDTO;
import org.example.capstonebackend.DTO.CreateBookForUserDTO;
import org.example.capstonebackend.DTO.UserUpdateDTO;
import org.example.capstonebackend.model.Book;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IUserRepository;
import org.example.capstonebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;


//CREATE
    //create user
    //this lives under the auth controller

//READ
    //get user by id

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/users/{userId}/books/{bookId}")
    public ResponseEntity<?> addBookToUser(@PathVariable Integer userId, @PathVariable Integer bookId) {
        try {
            User user = userService.addBookToUser(userId, bookId);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("users/{userId}/books")
    public ResponseEntity<?> createBookForUser(@PathVariable Integer userId, @RequestBody CreateBookForUserDTO createBookForUserDTO) {
        try {
            User user = userService.createBookForUser(userId, createBookForUserDTO.getTitle());
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(false).data(user.getBook()).status(HttpStatus.OK).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(true).message("failed to retrieve book").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    //get user by email
    @GetMapping("/users/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    //get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

//UPDATE
    //update User
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(user);
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(false).data(updatedUser).message("User updated successfully").status(HttpStatus.OK).build();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(true).message("unable to update user, please try again and fill out all fields").status(HttpStatus.BAD_REQUEST).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


//DELETE
    //delete user
@DeleteMapping("users/{id}")
public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
    try {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id " + id + " has been deleted.");
    } catch (RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}


}
