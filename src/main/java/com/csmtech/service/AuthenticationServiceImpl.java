package com.csmtech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.entity.UserMaster;
import com.csmtech.repository.UserMasterRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserMasterRepository repo;

	@Override
	public UserMaster findByEmail(String email) {
		return repo.getUserByEmail(email);
	}
}
