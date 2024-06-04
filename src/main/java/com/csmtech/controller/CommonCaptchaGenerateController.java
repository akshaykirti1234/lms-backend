package com.csmtech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.service.CommonCaptchaGenerateService;
import com.csmtech.util.CommonCaptcha;

@RestController
@CrossOrigin("*")
@RequestMapping("/commonCaptchaGenerator")
public class CommonCaptchaGenerateController {
	
	@Autowired
	private CommonCaptchaGenerateService captchaService;
	
	private static final Logger logger = LoggerFactory.getLogger(CommonCaptchaGenerateController.class);

	@GetMapping("/generate")
    public ResponseEntity<?> generateCaptcha() {
		logger.info("Execute generateCaptcha() Method ..!!");
		try {
			CommonCaptcha captcha = captchaService.generateCaptcha();
			if(captcha != null) {
				return ResponseEntity.ok(captcha);
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while generating captcha : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

}