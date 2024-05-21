package org.example.capstonebackend.controller;

import org.apache.coyote.Response;
import org.example.capstonebackend.model.Auth;
import org.example.capstonebackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {


    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

//CREATE - we want to be able to prevent duplication of email address usage.

    //add auth
    @PostMapping("/auths")
    public ResponseEntity<Auth> addAuth(@RequestBody Auth auth) throws Exception {
        try {
            //trying to add auth
            Auth addedAuth = authService.addAuth(auth);
            //if successful, return OK(200) response with the added auth
            return ResponseEntity.ok(addedAuth);
        } catch (Exception e) {
            //if an exception occurs during adding the auth, return Bad Request (400) response
            return ResponseEntity.badRequest().build();
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

    //getAllAuths

    @GetMapping("/auths")
    public List<Auth> getAllAuths() {
        List<Auth> auths = authService.getAllAuths();
        return auths;
    }

    //get auth by email
    @GetMapping("/auths/email/{email}")
    public ResponseEntity<Auth> getAuthByEmail (@PathVariable String email) {
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

//DELETE
    //delete auth
    @DeleteMapping("/auths/{id}")
    public void deleteAuth(@PathVariable int id) {
        authService.deleteAuth(id);
    }
}
