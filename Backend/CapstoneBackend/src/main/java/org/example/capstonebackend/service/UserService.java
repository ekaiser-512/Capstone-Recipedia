package org.example.capstonebackend.service;

import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IUserRepository;
import org.example.capstonebackend.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    ModelMapperUtil modelMapperUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User registerUser(User newUser) throws Exception{
        User existingUser = userRepository.findByEmail(newUser.getEmail()).orElse(null);
        if(existingUser != null){
            throw new Exception("user with email " + newUser.getEmail() + " already exists");
        }
        String rawPassword = newUser.getPassword();
        String encodePassword = passwordEncoder.encode(rawPassword);

        newUser.setPassword(encodePassword);
        return userRepository.save(newUser);
    }

    public User loginUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow( () -> new RuntimeException("User with email " + user.getEmail() + " not found"));
        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("The provided password is incorrect");
        }
        return existingUser;
    }

//READ
    //get user by id
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    //get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));
    }

    //get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//UPDATE
    public User updateUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User with email " + user.getEmail() + " not found"));

        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        return userRepository.save(existingUser);
    }

//DELETE
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        userRepository.delete(user);
    }
}
