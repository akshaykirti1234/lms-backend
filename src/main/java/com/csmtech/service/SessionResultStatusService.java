package com.csmtech.service;

import java.util.List;

import com.csmtech.entity.SessionResultStatus;

public interface SessionResultStatusService {

	List<SessionResultStatus> getSessionResultStatus(Integer userId);

	List<SessionResultStatus> getSessionResultBySessionIdUserId(Integer sessionId, Integer userId);

}
