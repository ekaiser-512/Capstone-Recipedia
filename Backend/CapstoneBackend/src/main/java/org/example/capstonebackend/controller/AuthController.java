package org.example.capstonebackend.controller;

import org.example.capstonebackend.DTO.CommonResponseDTO;
import org.example.capstonebackend.DTO.UserLoginDTO;

import org.example.capstonebackend.model.Auth;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.service.AuthService;
import org.example.capstonebackend.service.UserService;
import org.example.capstonebackend.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    //holds user verification concerns
    private final AuthService authService;

    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

//CREATE - we want to be able to prevent duplication of email address usage.

//    //todo check if the user exists and if so sends back a status of ok as well as the username.

    @PostMapping("/users/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(false).data(newUser).message("User created successfully").status(HttpStatus.OK).build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(true).message("User exists or form is missing information").status(HttpStatus.BAD_REQUEST).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            User existingUser = userService.loginUser(user);
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(false).data(existingUser).message("User authenticated successfully").status(HttpStatus.OK).build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(true).message("Email or password incorrect, please try again").status(HttpStatus.BAD_REQUEST).build();
            return ResponseEntity.badRequest().body(response);
        }

    }

//READ

    //get auth by ID
    @GetMapping("/auths/{id}")
    public ResponseEntity<Auth> getAuthById(@PathVariable int id) throws Exception {
        try {
            Auth auth = authService.getAuthById(id);
            return ResponseEntity.ok(auth);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //get auth by email
    @GetMapping("/auths/email/{email}")
    public ResponseEntity<Auth> getAuthByEmail(@PathVariable String email) {
        try {
            Auth auth = authService.getAuthByEmail(email);
            return ResponseEntity.ok(auth);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//UPDATE
    //update auth
    @PutMapping("/auths/{id}")
    public ResponseEntity<?> updateAuth(@PathVariable int id, @RequestBody Auth auth) throws Exception {
        try {
            Auth updatedAuth = authService.updateAuth(id, auth);
            return ResponseEntity.ok(updatedAuth);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    //we do not want to allow user to delete auth. They would have to delete the entire user
    //to prevent an account lockout bug

}