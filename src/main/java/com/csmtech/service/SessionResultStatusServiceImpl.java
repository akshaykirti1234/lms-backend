package com.csmtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.csmtech.entity.SessionResultStatus;
import com.csmtech.repository.SessionResultStatusRepository;

@Service
public class SessionResultStatusServiceImpl implements SessionResultStatusService {

	@Autowired
	private SessionResultStatusRepository sessionResultStatusRepository;

	@Override
	public List<SessionResultStatus> getSessionResultStatus(Integer userId) {
		return sessionResultStatusRepository.getSessionResultStatus(userId);
	}

	@Override
	public List<SessionResultStatus> getSessionResultBySessionIdUserId(Integer sessionId, Integer userId) {		// TODO Auto-generated method stub
		return sessionResultStatusRepository.getSessionResultBySessionIdUserId(sessionId,userId);
	}

}