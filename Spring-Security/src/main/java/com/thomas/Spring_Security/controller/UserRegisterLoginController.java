package com.thomas.Spring_Security.controller;

import com.thomas.Spring_Security.config.SpringConfig;
import com.thomas.Spring_Security.model.JwtService;
import com.thomas.Spring_Security.model.User;
import com.thomas.Spring_Security.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegisterLoginController {

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("register")
    public ResponseEntity<User> userRegister(@RequestBody User user){

        return new ResponseEntity<>(userRegisterService.saveUser(user) , HttpStatus.CREATED);

    }

    @PostMapping("login")
    public ResponseEntity<String> userLogin(@RequestBody User user){

        // Need to create a Authentication Manager class and holds an object of it
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if (authentication.isAuthenticated())
            return new ResponseEntity<>(jwtService.generateToken(user.getUsername()),HttpStatus.OK);
        else
            return  new ResponseEntity<>("failure",HttpStatus.UNAUTHORIZED);
    }

}
