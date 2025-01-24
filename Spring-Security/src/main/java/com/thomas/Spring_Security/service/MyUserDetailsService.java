package com.thomas.Spring_Security.service;

import com.thomas.Spring_Security.model.User;
import com.thomas.Spring_Security.model.UserPrincipal;
import com.thomas.Spring_Security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUsername(username);//U can't return User u have to return USERDETAILSSERVICE
        if(user == null){
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }

        return new UserPrincipal(user);
        //need to return in type of UserDetails , since it is inteeface we have  implemented in UserPrincipal class
    }
}
