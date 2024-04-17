package com.csmtech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.entity.UserMaster;
import com.csmtech.repository.UserMasterRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	Logger logger=LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	private UserMasterRepository repo;

	@Override
	public UserMaster findByEmail(String email) {
		logger.info("findByEmail method of AuthenticationServiceImpl is executed");
		return repo.getUserByEmail(email);
	}
}
