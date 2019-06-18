package com.bridgelabz.app.controller;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.app.model.LoginRequest;
import com.bridgelabz.app.model.User;
import com.bridgelabz.app.repository.UserRepository;
import com.bridgelabz.app.service.UserService;
import com.bridgelabz.app.util.JWTUtil;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private JavaMailSender sender;

//SEND EMAIL
    @RequestMapping("/sendMail")
    public String sendMail(@RequestBody User user) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(user.getEmail());
            helper.setText("Greetings :)");
            helper.setSubject("Mail From Spring Boot");
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> getUserByLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest reuest, HttpServletResponse response) {

        String token = userService.login(loginRequest);
        response.setHeader("token", token);

        System.out.println("token is ********* :" + token);
          //return "user->" + token;
        if (token != null) {
        	response.setHeader("token", token);
        	response.addHeader("Access-control-Allow-Headers", "*");
        	response.addHeader("Access-control-Expose-Headers", "*");
        	return new ResponseEntity<>( HttpStatus.OK);
        	}
        	else {
        	return new ResponseEntity<>("{invalid user}", HttpStatus.BAD_REQUEST);
        	}
        	}


    @PutMapping(value = "/updateuser")
    public void updateUser(@RequestBody User user, HttpServletRequest request) {
        System.out.println("I am  token at update method :" + request.getHeader("token"));
        userService.update(request.getHeader("token"), user);
    }

    @DeleteMapping(value = "/deleteuser")
    public void deleteUser(HttpServletRequest request) {

        System.out.println("I am  token at delete method :" + request.getHeader("token"));
        boolean b = userService.delete(request.getHeader("token"));
        System.out.println("-->" + b);

    }

    @PostMapping(value = "/forgotpassword")
    public String forgotPassword(@RequestBody User user, HttpServletRequest request) {
        User userInfo = userService.getUserInfoByEmail(user.getEmail());

        if (userInfo != null) {
            String token = JWTUtil.jwtToken("secretKey", userInfo.getId());
            
            StringBuffer requestUrl = request.getRequestURL();
            System.out.println(requestUrl);
            String forgotPasswordUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
            forgotPasswordUrl = forgotPasswordUrl + "/resetpassword/" +"token="+ token;
            System.out.println(forgotPasswordUrl);
            String subject="FOR FORGOT PASSWORD";
            
            userService.sendMail(userInfo, forgotPasswordUrl,subject);
            return "Mail Sent Successfully";
        }
        else
            return "not sent";    
    }

    @PutMapping(value = "/resetpassword")
    public void resetPassword(@RequestBody User user, HttpServletRequest request) {
        int id = JWTUtil.tokenVerification(request.getHeader("token"));

        if (id != 0) {

            Optional<User> userinfo = userService.findById(id);
            User usr = userinfo.get();
            usr.setPassword(user.getPassword());
            userService.update(request.getHeader("token"), usr);
        }

    }
    @PostMapping(value = "/sendtomail")
    public String sendToMail(@RequestBody User user, HttpServletRequest request) {
        User userInfo = userService.getUserInfoByEmail(user.getEmail());

        if (userInfo != null) {
            String token = JWTUtil.jwtToken("secretKey", userInfo.getId());
            
            StringBuffer requestUrl = request.getRequestURL();
            System.out.println(requestUrl);
            String forgotPasswordUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
            forgotPasswordUrl = forgotPasswordUrl + "/activestatus/" +"token="+ token;
            System.out.println(forgotPasswordUrl);
            String subject="Active Status";
            
            userService.sendMail(userInfo, forgotPasswordUrl,subject);
            return "Mail Sent Successfully"+userInfo;
        }
        else 
            return "Not Sent";
    }


@PutMapping(value = "/activeStatus")
public void activeStatus( HttpServletRequest request) {
    int id = JWTUtil.tokenVerification(request.getHeader("token"));

    if (id != 0) {

        Optional<User> userinfo = userService.findById(id);
        User usr = userinfo.get();
        usr.setStatus("1"); 
        userService.update(request.getHeader("token"), usr);
    }
}

@GetMapping(value="/fetch")
public List<User> fetch(HttpServletRequest request)
{
	return userRepository.findAll();
	
}
}