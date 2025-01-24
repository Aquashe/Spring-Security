package com.thomas.Spring_Security.controller;

import com.thomas.Spring_Security.model.User;
import com.thomas.Spring_Security.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    @PostMapping("register")
    public ResponseEntity<User> userRegister(@RequestBody User user){

        return new ResponseEntity<>(userRegisterService.saveUser(user) , HttpStatus.CREATED);

    }
}
