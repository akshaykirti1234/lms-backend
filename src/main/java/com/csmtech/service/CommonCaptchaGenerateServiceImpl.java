package com.csmtech.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.csmtech.util.CommonCaptcha;
import com.csmtech.util.CommonCaptchaGenerate;

@Service
public class CommonCaptchaGenerateServiceImpl implements CommonCaptchaGenerateService {

	private static final Logger logger = LoggerFactory.getLogger(CommonCaptchaGenerateServiceImpl.class);
	
	@Override
	public CommonCaptcha generateCaptcha() {
		try {
            logger.info("Execute generateCaptcha() Method of CommonCaptchaGenerateServiceImpl class....!!");
            SecureRandom rand = SecureRandom.getInstanceStrong();
            Integer number1 = rand.nextInt(9) + 1;
            Integer number2 = rand.nextInt(9) + 1;
            String operator = getRandomOperator();
            Integer result = performOperation(number1, number2, operator);
            String captchaText = number1 + " " + operator + " " + number2 + " = ?";
            String captchaId = UUID.randomUUID().toString();
            CommonCaptchaGenerate.put(captchaId, result);
            return new CommonCaptcha(captchaId, captchaText);
        } catch (NoSuchAlgorithmException e) {
            logger.error("An error occurred while generating captcha.", e);
            // Handle the exception, return appropriate error response, or throw a custom exception
            // For now, returning null
            return null;
        }
	}
	
	
	private String getRandomOperator() throws NoSuchAlgorithmException {
        try {
            String[] operators = { "+", "*" };
            SecureRandom rand = SecureRandom.getInstanceStrong();
            int randomIndex = rand.nextInt(operators.length);
            return operators[randomIndex];
        } catch (NoSuchAlgorithmException e) {
            logger.error("An error occurred while getting random operator.", e);
            throw e; // Re-throwing the exception as it cannot be handled here
        }
    }

    private int performOperation(int number1, int number2, String operator) {
        try {
            switch (operator) {
                case "+":
                    return number1 + number2;
                case "*":
                    return number1 * number2;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        } catch (IllegalArgumentException e) {
            logger.error("An error occurred while performing operation.", e);
            // Handle the exception, return appropriate error response, or throw a custom exception
            // For now, returning 0
            return 0;
        }
    }

}
