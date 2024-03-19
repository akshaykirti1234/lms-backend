package com.csmtech.service;

import org.springframework.http.ResponseEntity;

import com.csmtech.dto.UserMasterDTO;

public interface UserMasterService {

	ResponseEntity<?> loginValidate(UserMasterDTO userMasterDTO);

}
