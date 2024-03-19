package com.csmtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.csmtech.dto.UserMasterDTO;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.UserMasterRepository;

@Service
public class UserMasterServiceImpl implements UserMasterService {

	@Autowired
	private UserMasterRepository userMasterRepository;

	@Override
	public ResponseEntity<?> loginValidate(UserMasterDTO userMasterDTO) {
		String emailId = userMasterDTO.getEmailId();
		String password = userMasterDTO.getPassword();

		UserMaster user = userMasterRepository.getUserByEmail(emailId);

		if (user.getUserId() != null) {
			if (user.getPassword().equals(password)) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public ResponseEntity<?> getAllUsers() {
		try {
			List<Object> userList = userMasterRepository.getAllUsers();
			System.out.println(userList);
			return new ResponseEntity<>(userList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
