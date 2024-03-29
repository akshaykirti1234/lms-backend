package com.csmtech.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.LoginRequestDto;
import com.csmtech.entity.UserMaster;
import com.csmtech.service.AuthenticationService;
import com.csmtech.service.LmsUserDetailsServiceImpl;
import com.csmtech.util.CommonCaptchaGenerate;
import com.csmtech.util.CommonConstant;
import com.csmtech.util.JwtUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthenticationController {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private LmsUserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthenticationService service;

	// method will work generate Token and login

	@SuppressWarnings({ "unused", "rawtypes" })
	@PostMapping("/generate-token")
	public ResponseEntity generateToken(@RequestBody LoginRequestDto authRequest) throws Exception {

		Boolean validateCaptcha = captchaValidation(authRequest);

		if (!validateCaptcha) {

			JSONObject response = new JSONObject();

			response.put(CommonConstant.STATUS_KEY, 223);

			response.put(CommonConstant.RESULT, "Invalid captcha");

			return ResponseEntity.ok(response.toString());

		}
		UserMaster getalldata = null;
		getalldata = service.findByEmail(authRequest.getEmail());
		try {
			log.info("inside try method of authentication");
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
			log.info("afteer auth");
		} catch (Exception ex) {
			log.info("exception in authenticating user !!" + ex.getMessage());
			ex.printStackTrace();
			JSONObject response = new JSONObject();
			response.put(CommonConstant.STATUS_KEY, 500);
			response.put(CommonConstant.RESULT, CommonConstant.INVALID_USERNAME_PASSWORD);
			return ResponseEntity.ok(response.toString());
		}
		log.info(" :: Execution end tookan return");
		JSONObject response = new JSONObject();
		response.put(CommonConstant.STATUS_KEY, 200);
		response.put("fullName", getalldata.getFullName());
		response.put("emailId", getalldata.getEmailId());
		response.put("mobileNo", getalldata.getContactNo());
		response.put("userType", getalldata.getUserType().getTypeName());
		response.put(CommonConstant.RESULT,
				jwtUtil.generateToken(userDetailsService.loadUserByUsername(authRequest.getEmail())));
		return ResponseEntity.ok(response.toString());
	}

	// Validate C

	private boolean captchaValidation(LoginRequestDto authRequest) {
		boolean validateCaptcha;
		Integer captchaResult = CommonCaptchaGenerate.get(authRequest.getCaptchaId());
		if (captchaResult != null && authRequest.getAnswer().equals(captchaResult)) {
			CommonCaptchaGenerate.remove(authRequest.getCaptchaId());
			validateCaptcha = true;
		} else {
			validateCaptcha = false;
		}
		return validateCaptcha;
	}
}
