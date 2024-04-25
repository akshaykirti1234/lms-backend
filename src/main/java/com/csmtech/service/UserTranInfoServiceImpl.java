package com.csmtech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.csmtech.dto.UserTranInfoDto;
import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.UserMaster;
import com.csmtech.entity.UserTranInfo;
import com.csmtech.repository.UserTranInfoRepository;

@Service
public class UserTranInfoServiceImpl implements UserTranInfoService {

	Logger logger = LoggerFactory.getLogger(UserTranInfoService.class);

	@Autowired
	private UserTranInfoRepository userTranInfoRepository;

	@Override
	public ResponseEntity<?> saveUserInfo(UserTranInfoDto userTranInfoDto) {
		try {
			// Validate userTranInfoDto
			if (userTranInfoDto == null || userTranInfoDto.getUserId() == null || userTranInfoDto.getSessionId() == null
					|| userTranInfoDto.getStartTime() == null || userTranInfoDto.getEndTime() == null) {
				// Return bad request status if the DTO is invalid
				return new ResponseEntity<>("Invalid userTranInfoDto", HttpStatus.BAD_REQUEST);
			}

			// Create SessionMaster and UserMaster objects
			SessionMaster sessionMaster = new SessionMaster();
			UserMaster userMaster = new UserMaster();

			// Construct SessionMaster and UserMaster objects
			sessionMaster.setSessionId(userTranInfoDto.getSessionId());
			userMaster.setUserId(userTranInfoDto.getUserId());

			// Create UserTransInfo object
			UserTranInfo userTranInfo = new UserTranInfo();

			// Construct UserTranInfo object
			userTranInfo.setUserMaster(userMaster);
			userTranInfo.setSessionMaster(sessionMaster);
			userTranInfo.setStartTime(userTranInfoDto.getStartTime());
			userTranInfo.setEndTime(userTranInfoDto.getEndTime());
			userTranInfo.setRating(userTranInfoDto.getRating());

			System.out.println(userTranInfoDto.getRating());

			// Save userTranInfo
			UserTranInfo savedUserTranInfo = userTranInfoRepository.save(userTranInfo);

			if (savedUserTranInfo.getUserTransId() != null) {
				// Return success status if userTranInfo is saved successfully
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				// Return internal server error status if userTranInfo save fails
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			// Log the error and return internal server error status if an exception occurs
			logger.error("Error saving userTranInfo: " + e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
