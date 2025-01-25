package com.thomas.Spring_Security.config;

import com.thomas.Spring_Security.model.JwtService;
import com.thomas.Spring_Security.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter  extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token =null;
        String username = null;
        if(authHeader != null && authHeader.startsWith("Bearer")){
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);//Its in enocded format
        }

        //checking username is not null and authentication object is present
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = (UserDetails) context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            if(jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //Continuing the filter chain
        filterChain.doFilter(request,response);
    }
}
