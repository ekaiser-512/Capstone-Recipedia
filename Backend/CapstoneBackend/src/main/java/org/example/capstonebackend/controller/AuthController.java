package org.example.capstonebackend.controller;

import org.apache.coyote.Response;
import org.example.capstonebackend.model.Auth;
import org.example.capstonebackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;

@RestController
public class AuthController {

    //holds user verification concerns

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

//CREATE - we want to be able to prevent duplication of email address usage.

    //add auth
    //Notify users if the chosen username is already in use.
    //todo check if the user exists and if so sends back a status of ok as well as the username.
    @PostMapping("/users/signup")
    public ResponseEntity<Auth> userSignup(@RequestBody Auth auth) throws Exception {
        try {
            //trying to add auth
            Auth addedAuth = authService.userSignup(auth);
            //if successful, return OK(200) response with the added auth
            return ResponseEntity.ok(addedAuth);
        } catch (Exception e) {
            //if an exception occurs during adding the auth, return Bad Request (400) response
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> userLogin(@RequestBody Auth auth) {
        try {
            Auth existingAuth = authService.getAuthByEmail(auth.getEmail());
            if (Objects.equals(existingAuth.getPassword(), auth.getPassword()) &&
                    Objects.equals(existingAuth.getEmail(), auth.getEmail())) {
                return ResponseEntity.ok(true);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //get email out of the incoming auth object (Request body)
        //look up auth object from auth service given that email
        //compare password from request body to auth object we just looked up
        //if == ok, if !== unauthorized
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

}