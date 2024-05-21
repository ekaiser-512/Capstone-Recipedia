package org.example.capstonebackend.service;

import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

//CREATE
    //add user
public User addUser(User user) throws Exception {
    // Check if a post with the same title already exists
    Optional<Object> userExists = Optional.of(userRepository.findById(user.getId()));

    // If a post with the same title exists, throw an exception
    if(userExists.isPresent()) {
        throw new Exception("user with id " + user.getId() + " already exists");
    }
    // If no post with the same title exists, save the new post and return it
    return userRepository.save(user);
}
}
