package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> userList(){
       return userService.findAllUser();
    }

    @PostMapping()
    public User create(@RequestBody User request){
        return userService.create(request);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id){
        return userService.getUser(id);
    }

}
