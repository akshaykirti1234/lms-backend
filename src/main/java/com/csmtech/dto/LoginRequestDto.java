package com.csmtech.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

	private String email;

	private String password;

	private Integer answer;

	private String captchaId;
}
