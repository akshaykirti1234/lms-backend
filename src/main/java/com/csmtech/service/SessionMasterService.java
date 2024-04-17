package com.csmtech.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.csmtech.dto.SessionMasterDto;
import com.csmtech.entity.SessionMaster;

public interface SessionMasterService {

	SessionMaster saveSessionMaster(SessionMasterDto dto);

	SessionMasterDto getSessionMasterById(Integer id);

	void deleteSessionMasterById(Integer id);

	List<Map<String, Object>> getAllSessionMaster();

	Boolean checkIsLastSession(Integer id);

	Boolean checkBoxValidation(Integer id);

	ResponseEntity<?> getSessionByScheduleId(Integer scheduleId);

}
