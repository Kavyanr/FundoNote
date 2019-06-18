package com.bridgelabz.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.app.model.User;
import com.bridgelabz.app.service.UserService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegistrationController {
    
    @Autowired
    private UserService userService;

    @PostMapping(value = "/registration")
    public ResponseEntity<String> createUser(@RequestBody User user,HttpServletRequest request) {

         userService.userRegistration(user,request);
         return new ResponseEntity<>("{registration succesfull}", HttpStatus.OK);
    }
    


}