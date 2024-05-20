package org.example.capstonebackend.service;

import org.example.capstonebackend.model.Auth;
import org.example.capstonebackend.repository.IAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final IAuthRepository authRepository;

    @Autowired
    public AuthService(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

//CREATE

    //add Auth
    public boolean addAuthByEmail(Auth auth) {
        if(authRepository.existsByEmail(auth.getUserEmail())) {
            return false;
        }
        authRepository.save(auth);
        return true;
    }

//READ

    //get auth by ID
    public Auth getAuthById(Integer id) throws Exception {
        return authRepository.findById(id)
                .orElseThrow(() -> new Exception ("User with auth " + id + " not found."));
    }

    //get all auths
    public List<Auth> getAllAuths() {
        return authRepository.findAll();
    }

    //get auth by email
    public Auth getAuthByEmail(String email) throws Exception {
        return (Auth) authRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User with email " + email + " not found"));
    }

//UPDATE

    //update auth
    public Auth updateAuth(int id, Auth auth) throws Exception {
        Auth oldAuth = authRepository.findById(id).orElse(null);

        if (oldAuth == null) {
            throw new Exception ("User with authId " + id + " not found");
        }
        oldAuth.setUserEmail(auth.getUserEmail());
        oldAuth.setUserPassword(auth.getUserPassword());

        return authRepository.save(oldAuth);
    }

//DELETE
    //Will live under User as this is tied to it.

}
