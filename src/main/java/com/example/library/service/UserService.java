package com.example.library.service;

import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    public User getUser(Long id){
       User user = userRepository.findById(id).get();
       return user;
    }

    public User create(User request){
        return userRepository.save(request);
    }
}
