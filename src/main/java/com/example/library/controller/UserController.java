package com.example.library.controller;

import com.example.library.dto.UserDTO;
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
    public List<UserDTO> userList(){
       return userService.findAllUser();
    }

    @PostMapping()
    public UserDTO create(@RequestBody UserDTO request){
        return userService.create(request);
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody UserDTO request){
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public UserDTO delete(@PathVariable Long id){
        return userService.delete(id);
    }
}
