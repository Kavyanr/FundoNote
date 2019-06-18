package com.bridgelabz.app.serviceimpl;

import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bridgelabz.app.model.LoginRequest;
import com.bridgelabz.app.model.User;
import com.bridgelabz.app.repository.UserRepository;
import com.bridgelabz.app.service.UserService;
import com.bridgelabz.app.util.Encryption;
import com.bridgelabz.app.util.JWTUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRep;
    @Autowired
   private JavaMailSender sender;
   
    String secretKey;
    String subject;

    @Override
    public String login(LoginRequest loginReq) {
    	Optional<User> maybeUser = userRep.findByEmailAndPassword(loginReq.getEmail(),
    			Encryption.encryptedPassword(loginReq.getPassword()));
    	System.out.println(maybeUser);

    	if (maybeUser.isPresent()) {
    	System.out.println("Sucessful login");
    	return JWTUtil.jwtToken(Encryption.encryptedPassword(loginReq.getPassword()), maybeUser.get().getId());
    	} 
    	else
    	return "Invalid User";
    }
    @Override
    public User update(String token, User user) {
        int verifiedUserId = JWTUtil.tokenVerification(token);

        Optional<User> maybeUser = userRep.findById(verifiedUserId);
        User presentUser = maybeUser.map(existingUser -> {
            existingUser.setEmail(user.getEmail() != null ? user.getEmail() : maybeUser.get().getEmail());
            existingUser.setPhonenumber(
                    user.getPhonenumber() != null ? user.getPhonenumber() : maybeUser.get().getPhonenumber());
            existingUser.setName(user.getName() != null ? user.getName() : maybeUser.get().getName());
            existingUser
                    .setPassword(user.getPassword() != null ? Encryption.encryptedPassword(user.getPassword()) : maybeUser.get().getPassword());
            return existingUser;
        }).orElseThrow(() -> new RuntimeException("User Not Found"));

        return userRep.save(presentUser);
    }

    @Override
    public boolean delete(String token) {
        int verifiedUserId = JWTUtil.tokenVerification(token);
        Optional<User> maybeUser = userRep.findById(verifiedUserId);
        return maybeUser.map(existingUser -> {
            userRep.delete(existingUser);
            return true;
        }).orElseGet(() -> false);
    }

    @Override
    public User userRegistration(User user,HttpServletRequest request) {
        user.setPassword(Encryption.encryptedPassword(user.getPassword()));
        
        userRep.save(user);
        Optional<User> user1= userRep.findById(user.getId());
        if(user1 !=null) {
            System.out.println("Sucessfull reg");
            //Optional<User> maybeUser = userRep.findById(user.getId());
            
            String tokenGen = JWTUtil.jwtToken("secretKey", user1.get().getId());
            
            User u=user1.get();
            StringBuffer requestUrl = request.getRequestURL();
            System.out.println(requestUrl);
            String forgotPasswordUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
            forgotPasswordUrl = forgotPasswordUrl + "/activestatus/" +"token="+ tokenGen;
            System.out.println(forgotPasswordUrl);
            String subject="User Activation";
            
            String s= sendMail(u, forgotPasswordUrl,subject);
            //return "Mail Sent Successfully";
            return u;
                
        }else {
            System.out.println("Not sucessful reg");
        }
        return null;
    }

   
   
    @Override
    public User getUserInfoByEmail(String email) {
        return userRep.findByEmail(email);
        
                        
        
    }
    
    public  String sendMail(User user,String urlPattern,String subject) {
        
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        
        

        try {
            helper.setTo(user.getEmail());
            helper.setText(urlPattern);
            helper.setSubject(subject);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
        
            
    }

    public Optional<User> findById(int id) {
        return userRep.findById(id);
        
    }

}