package com.csmtech.service;

import org.springframework.http.ResponseEntity;

import com.csmtech.dto.UserTranInfoDto;

public interface UserTranInfoService {

	ResponseEntity<?> saveUserInfo(UserTranInfoDto userTranInfoDto);

}
