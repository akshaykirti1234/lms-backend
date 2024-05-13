package com.csmtech.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.csmtech.dto.ScheduleForMasterDto;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.exceptions.IsLastSessionException;
import com.csmtech.repository.ScheduleForMasterRepository;
import com.csmtech.repository.SessionMasterRepository;

@Service
public class ScheduleForMasterServiceImpl implements ScheduleForMasterService {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleForMasterServiceImpl.class);

	@Autowired
	private ScheduleForMasterRepository scheduleForMasterRepository;
	
	@Autowired
	private SessionMasterRepository sessionMasterRepository;
	

	@Override
	public ResponseEntity<?> saveScheduleForm(ScheduleForMasterDto scheduleForMasterDto) {
		logger.info("saveScheduleForm method of ScheduleForMasterServiceImpl is executed");
		ScheduleForMaster scheduleForMaster = new ScheduleForMaster();
		scheduleForMaster.setScheduleForId(scheduleForMasterDto.getScheduleForId());
		scheduleForMaster.setScheduleForName(scheduleForMasterDto.getScheduleForName());
		scheduleForMaster.setSubModule(scheduleForMasterDto.getSubModule());
		scheduleForMaster.setAuthor(scheduleForMasterDto.getAuthor());
		if (scheduleForMasterDto.getTechnology().getTechId() != null) {
			scheduleForMaster.setTechnology(scheduleForMasterDto.getTechnology());
		} else {
			scheduleForMaster.setTechnology(null);
		}
		scheduleForMaster.setIsAssessmentPrepared(false);
		scheduleForMaster.setNoOfSession(scheduleForMasterDto.getNoOfSession());
		scheduleForMaster.setNoOfHours(scheduleForMasterDto.getNoOfHours());
		scheduleForMaster.setCreatedBy(1);
		scheduleForMaster.setUpdatedBy(1);
		// Save
		ScheduleForMaster savedSchedule = scheduleForMasterRepository.save(scheduleForMaster);

		if (savedSchedule.getScheduleForId() != null) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public ResponseEntity<?> updateScheduleForm(ScheduleForMasterDto scheduleForMasterDto) throws IsLastSessionException {
		logger.info("updateScheduleForm method of ScheduleForMasterServiceImpl is executed");
		ScheduleForMaster scheduleForMaster = new ScheduleForMaster();
		scheduleForMaster.setScheduleForId(scheduleForMasterDto.getScheduleForId());
		scheduleForMaster.setScheduleForName(scheduleForMasterDto.getScheduleForName());
		scheduleForMaster.setSubModule(scheduleForMasterDto.getSubModule());
		scheduleForMaster.setAuthor(scheduleForMasterDto.getAuthor());
		if (scheduleForMasterDto.getTechnology().getTechId() != null) {
			scheduleForMaster.setTechnology(scheduleForMasterDto.getTechnology());
		}else {
			scheduleForMaster.setTechnology(null);
		}
		scheduleForMaster.setIsAssessmentPrepared(false);
		scheduleForMaster.setNoOfSession(scheduleForMasterDto.getNoOfSession());
		scheduleForMaster.setNoOfHours(scheduleForMasterDto.getNoOfHours());
		scheduleForMaster.setCreatedBy(1);
		scheduleForMaster.setUpdatedBy(1);
		
		@SuppressWarnings("deprecation")
		ScheduleForMaster sm = scheduleForMasterRepository.getById(scheduleForMasterDto.getScheduleForId());
		
		if(scheduleForMasterDto.getNoOfSession() > sm.getNoOfSession()) {
			sessionMasterRepository.updateIsLastSession(scheduleForMasterDto.getScheduleForId());
			ScheduleForMaster savedSchedule = scheduleForMasterRepository.save(scheduleForMaster);
			
			if (savedSchedule.getScheduleForId() != null) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		else if(scheduleForMasterDto.getNoOfSession() == sm.getNoOfSession()) {
			ScheduleForMaster savedSchedule = scheduleForMasterRepository.save(scheduleForMaster);
			if (savedSchedule.getScheduleForId() != null) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		else {
			logger.error("You can not enter number which is less than previous number.");
			throw new IsLastSessionException("You can not enter number which is less than previous number.");
		}
	}

	@Override
	public List<ScheduleForMaster> getAllScheduleForm() {
		logger.info("getAllScheduleForm method of ScheduleForMasterServiceImpl is executed");
		return scheduleForMasterRepository.getAllScheduleForm();
	}

	@Override
	public ResponseEntity<?> updateScheduleFor(Integer scheduleForId) {
		logger.info("updateScheduleFor method of ScheduleForMasterServiceImpl is executed");
		if (scheduleForId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Optional<ScheduleForMaster> scheduleForOptional = scheduleForMasterRepository.findById(scheduleForId);
		if (scheduleForOptional.isPresent()) {
			ScheduleForMaster scheduleForMaster = scheduleForOptional.get();
			return new ResponseEntity<>(scheduleForMaster, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> deleteScheduleFor(Integer scheduleForId) {
		logger.info("deleteScheduleFor method of ScheduleForMasterServiceImpl is executed");
		if (scheduleForId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		scheduleForMasterRepository.deleteScheduleFor(scheduleForId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@Override
	public List<Map<String, Object>> getScheduleForBySubModuleId(Integer id) {
		logger.info("getScheduleForBySubModuleId method of ScheduleForMasterServiceImpl is executed");
		return scheduleForMasterRepository.findBysubModuleId(id);
	}

	@Override
	public ResponseEntity<List<ScheduleForMaster>> getScheduleBySubModuleId(Integer submoduleId) {
		List<ScheduleForMaster> scheduleForList = scheduleForMasterRepository.getScheduleBySubModuleId(submoduleId);
		if (scheduleForList.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(scheduleForList);
		}
	}

	

}
