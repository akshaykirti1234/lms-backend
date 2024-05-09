package com.csmtech.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.csmtech.dto.ScheduleForMasterDto;
import com.csmtech.entity.ScheduleForMaster;

public interface ScheduleForMasterService {

	ResponseEntity<?> saveScheduleForm(ScheduleForMasterDto scheduleForMasterDto);

	List<ScheduleForMaster> getAllScheduleForm();

	ResponseEntity<?> updateScheduleFor(Integer scheduleForId);

	ResponseEntity<?> deleteScheduleFor(Integer scheduleForId);

	List<Map<String, Object>> getScheduleForBySubModuleId(Integer id);

	ResponseEntity<?> getScheduleBySubModuleId(Integer submoduleId);

	ResponseEntity<?> updateScheduleForm(ScheduleForMasterDto scheduleForMasterDto);

}
