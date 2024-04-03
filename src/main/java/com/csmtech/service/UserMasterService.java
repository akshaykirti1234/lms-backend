package com.csmtech.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.UserMasterDTO;
import com.csmtech.entity.UserMaster;

public interface UserMasterService {

	ResponseEntity<?> loginValidate(UserMasterDTO userMasterDTO);

	ResponseEntity<?> getAllUsers();

	UserMaster saveUserMaster(UserMasterDTO userMasterDto);

	List<Map<String, Object>> getUseMasterList();

	void deleteUserMaster(Integer userId);

	Map<String, Object> getUserMasterById(Integer userId);

	ResponseEntity<Map<String, Object>> uploadUserData(MultipartFile file) throws IOException;

	List<Map<String, Object>> gettEmailList();

	ResponseEntity<?> updatePassword(String passwordPayload);

}
