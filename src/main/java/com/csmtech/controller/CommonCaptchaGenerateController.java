package com.csmtech.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.util.CommonCaptcha;
import com.csmtech.util.CommonCaptchaGenerate;

@RestController
@CrossOrigin("*")
@RequestMapping("/commonCaptchaGenerator")
public class CommonCaptchaGenerateController {
	private static final Logger log = LoggerFactory.getLogger(CommonCaptchaGenerateController.class);

	@GetMapping("/generate")
	public CommonCaptcha generateCaptcha() throws NoSuchAlgorithmException {
		log.info("Execute  generateCaptcha() Method ..!!");
		SecureRandom rand = SecureRandom.getInstanceStrong();
		Integer number1 = rand.nextInt(9) + 1;
		Integer number2 = rand.nextInt(9) + 1;
		String operator = getRandomOperator();
		Integer result = performOperation(number1, number2, operator);
		String captchaText = number1 + " " + operator + " " + number2 + " = ?";
		String captchaId = UUID.randomUUID().toString();
		CommonCaptchaGenerate.put(captchaId, result);
		return new CommonCaptcha(captchaId, captchaText);
	}

	private String getRandomOperator() throws NoSuchAlgorithmException {
		String[] operators = { "+", "*" };
		SecureRandom rand = SecureRandom.getInstanceStrong();
		int randomIndex = rand.nextInt(operators.length);
		return operators[randomIndex];
	}

	private int performOperation(int number1, int number2, String operator) {
		switch (operator) {
		case "+":
			return number1 + number2;
		case "*":
			return number1 * number2;
		default:
			throw new IllegalArgumentException("Invalid operator: " + operator);
		}
	}
}
