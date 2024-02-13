package com.jwt.example.controllers;

import com.jwt.example.entity.User;
import com.jwt.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    //http://localhost:8081/home/user
    @Autowired
    private UserService userService ;

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/test")
    public String test() {
        this.logger.warn("This is working message");
        return "Testing message";
    }
    @GetMapping("/user")
    public List<User> getUser(){
        System.out.println("getting users");
        return this.userService.getUser();

    }
    @GetMapping("/current-user  ")
    public  String getLogInUser(Principal principal){
        return principal.getName();
    }
}
