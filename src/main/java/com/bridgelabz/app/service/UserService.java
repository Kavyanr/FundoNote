package com.bridgelabz.app.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.app.model.LoginRequest;
import com.bridgelabz.app.model.User;


public interface UserService {
	    public String login(LoginRequest user);

	    public User update(String token, User user);

	    public User userRegistration(User user, HttpServletRequest request);


	    public boolean delete(String token);
	    
	    
	    public User getUserInfoByEmail(String email);
	    
	    public  String sendMail(User user,String url,String subject);

	    public Optional<User> findById(int id);

	    

	

}
