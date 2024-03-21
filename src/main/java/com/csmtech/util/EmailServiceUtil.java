package com.csmtech.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.csmtech.repository.UserMasterRepository;

@Service
public class EmailServiceUtil {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private UserMasterRepository userRepo;

	public void sendInformationEmail(List<String> emails, String description) {
        // Implement logic to send information email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Information Email");
        message.setText(description);
        message.setTo(emails.toArray(new String[0]));
        javaMailSender.send(message);
    }

    public void sendPasswordEmail(List<String> emails) {
        for (String email : emails) {
            Map<String, Object> emailPassword = userRepo.getEmailPassword(email);
            String username = (String) emailPassword.get("emailid");
            String password = (String) emailPassword.get("normalpassword");
            if(username != null || password != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Your Credentials");
            message.setText("Username: " + username + "\nPassword: " + password);
            message.setTo(email);
            javaMailSender.send(message);
            }else {
            	System.out.println("Email address not found in the database: " + email);
            }
        }
    }

}
