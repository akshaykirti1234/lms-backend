package com.csmtech.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceUtil {
	
	@Autowired
    private JavaMailSender javaMailSender;

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
            
            String username = "user";
            String password = "password";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Your Credentials");
            message.setText("Username: " + username + "\nPassword: " + password);
            message.setTo(email);
            javaMailSender.send(message);
        }
    }

}
