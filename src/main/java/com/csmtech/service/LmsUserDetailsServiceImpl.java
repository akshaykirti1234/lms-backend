package com.csmtech.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.csmtech.entity.UserMaster;
import com.csmtech.repository.UserMasterRepository;

@Service
public class LmsUserDetailsServiceImpl implements UserDetailsService {
	@Autowired

	private UserMasterRepository repo;

	private static final Logger logger = LoggerFactory.getLogger(LmsUserDetailsServiceImpl.class);

	@Override

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.info("::  execution start of loadUserByUsername method");

		UserMaster entity = repo.getUserByEmail(username);

		logger.info(entity.toString());

		logger.info(":: execution end of loadUserByUsername method return to controller");

		return new org.springframework.security.core.userdetails.User(entity.getEmailId(), entity.getPassword(),

				new ArrayList<>());

	}
}
