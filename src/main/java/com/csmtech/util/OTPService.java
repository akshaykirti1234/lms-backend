package com.csmtech.util;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OTPService {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
    private static final long OTP_EXPIRY_DURATION_MS = 5 * 60 * 1000; // 5 minutes

    private Map<String, Map.Entry<String, Long>> otpMap = new ConcurrentHashMap<>();

    public String generateOTP(String email) {
        String otp = generateRandomOTP();
        otpMap.put(email, Map.entry(otp, System.currentTimeMillis() + OTP_EXPIRY_DURATION_MS));
        sendOTPEmail(email, otp);
        return otp;
    }

    public boolean verifyOTP(String email, String otp) {
        Map.Entry<String, Long> otpInfo = otpMap.get(email);
        if (otpInfo != null && otpInfo.getKey().equals(otp) && System.currentTimeMillis() <= otpInfo.getValue()) {
            otpMap.remove(email);
            return true;
        }
        return false;
    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    
    private void sendOTPEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP");
        message.setText("You requested for changing password. Your OTP is: " + otp);
        javaMailSender.send(message);
    }
}

