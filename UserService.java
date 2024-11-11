package com.hospital.hms.service;

import com.hospital.hms.model.User;
import com.hospital.hms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
     BCryptPasswordEncoder passwordEncoder;



    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    public List<User> getUser(){
        return userRepository.findAll();
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
    public User getUserById(long id)
    {
        return userRepository.findById(id).get();
    }
    public User updateUser(Long id,User userdetails)
    {
        User user=userRepository.findById(id).get();
        user.setProblem(userdetails.getProblem());
        user.setDate(userdetails.getDate());
        return userRepository.save(user);
    }
    public Optional<User> login(String username, String password) {
        if (username == null || password == null) {
            return Optional.empty(); // Return empty if username or password is null
        }

        return userRepository.findByUsername(username)
                .filter(user ->  {boolean matches = passwordEncoder.matches(password, user.getPassword());
        System.out.println("Password match: " + matches);
                return matches;});
    }

}
