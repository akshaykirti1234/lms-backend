package com.csmtech.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.csmtech.dto.SessionMasterDto;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.repository.ScheduleForMasterRepository;
import com.csmtech.repository.SessionMasterRepository;
import com.csmtech.repository.SubModuleRepository;

@Service
public class SessionMasterServiceImpl implements SessionMasterService {

	@Autowired
	private SessionMasterRepository sessionRepo;

	@Autowired
	private SubModuleRepository subModuleRepo;

	@Autowired
	private ScheduleForMasterRepository schRepo;

	@Override
	public SessionMaster saveSessionMaster(SessionMasterDto dto) {
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
		sessionRepo.deleteSession(id);
	}

	@Override
	public List<Map<String, Object>> getAllSessionMaster() {
		return sessionRepo.getAllSessionMaster();
	}

	@Override
	public Boolean checkIsLastSession(Integer id) {
		Boolean i = sessionRepo.checkIsLastSession(id);
		return i;
	}

	@Override
	public Boolean checkBoxValidation(Integer id) {
		String s = sessionRepo.checkBoxValidation(id);
		if (s.equals("true")) {
			return true;
		}
		return false;
	}

	@Override
	public ResponseEntity<?> getSessionByScheduleId(Integer scheduleId) {
		List<SessionMaster> sessionMastersList = sessionRepo.getSessionByScheduleId(scheduleId);
		if (sessionMastersList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(sessionMastersList);
	}

}
