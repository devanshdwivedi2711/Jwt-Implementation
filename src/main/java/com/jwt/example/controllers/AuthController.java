package com.jwt.example.controllers;

import com.jwt.example.entity.JwtRequest;
import com.jwt.example.entity.JwtResponse;
import com.jwt.example.security.JwtHelper;
import io.jsonwebtoken.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService ; // user information
    @Autowired
    private JwtHelper jwtHelper ; //for creating jwt
    @Autowired
    private  AuthenticationManager manager ; // authentication purpose

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token  = this.jwtHelper.generateToken(userDetails);

        JwtResponse response = new JwtResponse().builder().jwtToken(token).username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    private void doAuthenticate(String email , String password)
    {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email , password);
        try {
            manager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid Username or Password");
        }





    }
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(){
        return "Credentials Invalid";
    }
}
