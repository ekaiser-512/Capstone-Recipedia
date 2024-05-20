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
    @PostMapping("/auths/userEmail/{userEmail}")
    public ResponseEntity<Auth> addAuthByEmail(@RequestBody Auth auth) {
        try {
            if(auth.getUserEmail() == null || auth.getUserPassword() == null) {
                return ResponseEntity.badRequest().build();
            }
            boolean added = authService.addAuthByEmail(auth);
            if (added) {
                return ResponseEntity.status(HttpStatus.CREATED).body(auth);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } catch (Exception e) {
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
    //Will live under User as this is tied to it.
}
