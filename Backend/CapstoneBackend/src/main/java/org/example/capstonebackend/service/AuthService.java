package org.example.capstonebackend.service;

import org.example.capstonebackend.model.Auth;
import org.example.capstonebackend.repository.IAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthService {

    private final IAuthRepository authRepository;

    @Autowired
    public AuthService(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

//CREATE

    //add Auth
    public Auth userSignup(Auth auth) throws Exception {
        //check if an auth with the same email already exists
        Optional<Object> authExists = authRepository.findByEmail(auth.getEmail());

        //If an auth with the same email exists, throw an exception
        if (authExists.isPresent()) {
            throw new Exception("auth with email " + auth.getEmail() + " already exists");
        }
        //if no auth with the same email exists save the new auth and return it
        return authRepository.save(auth);
    }

//READ

    //get auth by ID
    public Auth getAuthById(Integer id) throws Exception {
        return authRepository.findById(id)
                .orElseThrow(() -> new Exception("User with auth " + id + " not found."));
    }

    //get auth by email -- this is also used by @Post "/users/login" to access database.
    public Auth getAuthByEmail(String email) throws Exception {
        return (Auth) authRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User with email " + email + " not found"));
    }

//UPDATE

    //update auth
    public Auth updateAuth(int id, Auth auth) throws Exception {
        Auth oldAuth = authRepository.findById(id).orElse(null);

        if (oldAuth == null) {
            throw new Exception("User with authId " + id + " not found");
        }
        oldAuth.setEmail(auth.getEmail());
        oldAuth.setPassword(auth.getPassword());

        return authRepository.save(oldAuth);
    }

}