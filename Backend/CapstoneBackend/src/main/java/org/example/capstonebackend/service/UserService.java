package org.example.capstonebackend.service;

import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional<User> userExists = userRepository.findByEmail(user.getEmail());

        // If a post with the same title exists, throw an exception
        if(userExists.isPresent()) {
            throw new Exception("user with id " + user.getUserId() + " already exists");
        }
        // If no post with the same title exists, save the new post and return it
        return userRepository.save(user);
    }

//READ
    //get user by id
    public User getUserById(Integer id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User with id " + id + " not found"));
    }

    //get user by email
    public User getUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User with email " + email + " not found"));
    }

    //get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//UPDATE
    //update user
    public User updateUser(int id, User user) throws Exception {
        User oldUser = userRepository.findById(id).orElse(null);

        if(oldUser == null) {
            throw new Exception("User with id " + id + " not found");
        }
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setDateOfBirth(user.getDateOfBirth());

        return userRepository.save(oldUser);
    }

//DELETE
    //delete user
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
