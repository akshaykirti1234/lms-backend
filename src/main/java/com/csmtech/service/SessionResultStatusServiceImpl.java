package com.csmtech.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.entity.SessionResultStatus;
import com.csmtech.repository.SessionResultStatusRepository;

@Service
public class SessionResultStatusServiceImpl implements SessionResultStatusService {
	
	private static final Logger logger=LoggerFactory.getLogger(SessionResultStatusServiceImpl.class);

	@Autowired
	private SessionResultStatusRepository sessionResultStatusRepository;

	@Override
	public List<SessionResultStatus> getSessionResultStatus(Integer userId) {
		logger.info("getSessionResultStatus method of SessionResultStatusServiceImpl is executed");
		return sessionResultStatusRepository.getSessionResultStatus(userId);
	}

	@Override
	public List<SessionResultStatus> getSessionResultBySessionIdUserId(Integer sessionId, Integer userId) {
		logger.info("getSessionResultBySessionIdUserId method of SessionResultStatusServiceImpl is executed");// TODO Auto-generated method stub
		return sessionResultStatusRepository.getSessionResultBySessionIdUserId(sessionId,userId);
	}

}