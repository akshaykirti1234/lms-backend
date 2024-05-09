package com.csmtech.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.csmtech.dto.SessionMasterDto;
import com.csmtech.entity.SessionMaster;
import com.csmtech.exceptions.SessionNotFoundException;

public interface SessionMasterService {

	SessionMaster saveSessionMaster(SessionMasterDto dto);

	SessionMasterDto getSessionMasterById(Integer id) throws SessionNotFoundException;

	void deleteSessionMasterById(Integer id) throws SessionNotFoundException;

	List<Map<String, Object>> getAllSessionMaster();

	Boolean checkIsLastSession(Integer id);

	Boolean checkBoxValidation(Integer id);

	ResponseEntity<?> getSessionByScheduleId(Integer scheduleId);

	ResponseEntity<?> getSessionByscheduleForIdAndUserId(Integer scheduleId, Integer userId);

}
