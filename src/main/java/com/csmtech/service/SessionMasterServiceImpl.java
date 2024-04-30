package com.csmtech.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.csmtech.dto.SessionMasterDto;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.SessionResultStatus;
import com.csmtech.entity.SubModule;
import com.csmtech.repository.ScheduleForMasterRepository;
import com.csmtech.repository.SessionMasterRepository;
import com.csmtech.repository.SessionResultStatusRepository;
import com.csmtech.repository.SubModuleRepository;

@Service
public class SessionMasterServiceImpl implements SessionMasterService {

	Logger logger = LoggerFactory.getLogger(SessionMasterServiceImpl.class);

	@Autowired
	private SessionMasterRepository sessionRepo;

	@Autowired
	private SubModuleRepository subModuleRepo;

	@Autowired
	private ScheduleForMasterRepository schRepo;

	@Autowired
	private SessionResultStatusRepository sessionResultStatusRepository;

	@Override
	public SessionMaster saveSessionMaster(SessionMasterDto dto) {
		logger.info("saveSessionMaster method of SessionMasterServiceImpl is executed");
		SessionMaster sm = new SessionMaster();
		if (dto.getSessionid() != 0) {
			sm.setSessionId(dto.getSessionid());
			sm.setUpdatedBy(1);
		}
		SubModule subModule = subModuleRepo.findById(dto.getSubModuleId()).get();
		sm.setSubModule(subModule);
		ScheduleForMaster sfm = schRepo.findById(dto.getScheduleForId()).get();
		sm.setScheduleFor(sfm);
		sm.setIsLastSession(dto.getIsLastSession());
		sm.setSessionName(dto.getSessionName());
		sm.setVideo(dto.getVideo());
		sm.setDocument(dto.getDocument());
		sm.setSessionDescription(dto.getDescription());
		sm.setCreatedBy(1);
		return sessionRepo.save(sm);
	}

	@Override
	public SessionMasterDto getSessionMasterById(Integer id) {
		logger.info("getSessionMasterById method of SessionMasterServiceImpl is executed");
		SessionMaster sm = sessionRepo.findById(id).get();
		SessionMasterDto dto = new SessionMasterDto();
		dto.setSessionid(sm.getSessionId());
		dto.setSessionName(sm.getSessionName());
		dto.setScheduleForId(sm.getScheduleFor().getScheduleForId());
		dto.setSubModuleId(sm.getSubModule().getSubmoduleId());
		dto.setVideo(sm.getVideo());
		dto.setDocument(sm.getDocument());
		dto.setDescription(sm.getSessionDescription());
		dto.setIsLastSession(sm.getIsLastSession());
		return dto;
	}

	@Override
	public void deleteSessionMasterById(Integer id) {
		logger.info("deleteSessionMasterById method of SessionMasterServiceImpl is executed");
		sessionRepo.deleteSession(id);
	}

	@Override
	public List<Map<String, Object>> getAllSessionMaster() {
		logger.info("getAllSessionMaster method of SessionMasterServiceImpl is executed");
		return sessionRepo.getAllSessionMaster();
	}

	@Override
	public Boolean checkIsLastSession(Integer id) {
		logger.info("checkIsLastSession method of SessionMasterServiceImpl is executed");
		Boolean i = sessionRepo.checkIsLastSession(id);
		return i;
	}

	@Override
	public Boolean checkBoxValidation(Integer id) {
		logger.info("checkBoxValidation method of SessionMasterServiceImpl is executed");
		String s = sessionRepo.checkBoxValidation(id);
		if (s.equals("true")) {
			return true;
		}
		return false;
	}

	@Override
	public ResponseEntity<?> getSessionByScheduleId(Integer scheduleId) {
		logger.info("getSessionByScheduleId method of SessionMasterServiceImpl is executed");
		List<SessionMaster> sessionMastersList = sessionRepo.getSessionByScheduleId(scheduleId);
		if (sessionMastersList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(sessionMastersList);
	}

	@Override
	public ResponseEntity<?> getSessionByscheduleForIdAndUserId(Integer scheduleId, Integer userId) {
		logger.info("getSessionByScheduleId method of SessionMasterServiceImpl is executed");
		List<SessionMaster> sessionMastersList = sessionRepo.getSessionByScheduleId(scheduleId);
//		boolean previous=false;
		for (SessionMaster sessionMaster : sessionMastersList) {
			System.out.println(sessionMaster.isResultStatus());
			System.out.println(userId);
			SessionResultStatus bySessionResult = sessionResultStatusRepository
					.findBySessionMaster_SessionIdAndUserMaster_UserId(sessionMaster.getSessionId(), userId);
			if (bySessionResult != null) {
//				previous=bySessionResult.getStatusOfResult();
				sessionMaster.setResultStatus(bySessionResult.getStatusOfResult());
			}
		}
		if (sessionMastersList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(sessionMastersList);
	}

}
