package com.csmtech.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.csmtech.dto.ScheduleForMasterDto;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.repository.ScheduleForMasterRepository;

@Service
public class ScheduleForMasterServiceImpl implements ScheduleForMasterService {

	@Autowired
	private ScheduleForMasterRepository scheduleForMasterRepository;

	@Override
	public ResponseEntity<?> saveScheduleForm(ScheduleForMasterDto scheduleForMasterDto) {

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
		scheduleForMaster.setDeletedFlag(false);

		// Save
		ScheduleForMaster savedSchedule = scheduleForMasterRepository.save(scheduleForMaster);

		if (savedSchedule.getScheduleForId() != null) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public List<ScheduleForMaster> getAllScheduleForm() {
		return scheduleForMasterRepository.getAllScheduleForm();
	}

	@Override
	public ResponseEntity<?> updateScheduleFor(Integer scheduleForId) {
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
		if (scheduleForId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		scheduleForMasterRepository.deleteScheduleFor(scheduleForId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@Override
	public List<Map<String, Object>> getScheduleForBySubModuleId(Integer id) {
		return scheduleForMasterRepository.findBysubModuleId(id);
	}

}
