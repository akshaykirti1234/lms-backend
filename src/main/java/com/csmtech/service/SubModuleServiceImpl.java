package com.csmtech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.csmtech.dto.SubModuleDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.repository.SubModuleRepository;

@Service
public class SubModuleServiceImpl implements SubModuleService {

	Logger logger=LoggerFactory.getLogger(SubModuleServiceImpl.class);
	
	@Autowired
	private SubModuleRepository subModuleRepository;

	@Override
	public ResponseEntity<?> getSubmoduleByModuleId(Integer moduleId) {
		logger.info("getSubmoduleByModuleId method of SubModuleServiceImpl is executed");
		List<SubModule> submoduleList = subModuleRepository.getSubmoduleByModuleId(moduleId);
		if (submoduleList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(submoduleList, HttpStatus.OK);
	}

	@Override
	public List<SubModule> getAllSubModules() {
		logger.info("getAllSubModules method of SubModuleServiceImpl is executed");
		return subModuleRepository.getAllSubModules();
	}

	@Override
	public Map<String, Object> saveSubModule(SubModuleDto subModuleDto) {
		logger.info("saveSubModule method of SubModuleServiceImpl is executed");
		Map<String, Object> response = new HashMap<>();
		try {

			SubModule entity = new SubModule();
			ModuleMaster moduleMaster = new ModuleMaster();
			moduleMaster.setModuleId(subModuleDto.getModuleId());

			if (subModuleDto.getSubmoduleId() != null) {
				entity.setSubmoduleId(subModuleDto.getSubmoduleId());
				entity.setUpdatedBy(1);
			}
			entity.setModuleMaster(moduleMaster);
			entity.setSubmoduleName(subModuleDto.getSubmoduleName());
			entity.setCreatedBy(1);

			subModuleRepository.save(entity);
			response.put("status", HttpStatus.CREATED);
			response.put("message", "Sub-Module data saved successfully");

		} catch (Exception e) {
			logger.info("Error occured in saveSubModule method of SubModuleServiceImpl"+e.getMessage());
			e.printStackTrace();
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Something went wrong");
		}
		return response;
	}

	@Override
	public List<Map<String, Object>> getAllSubModule() {
		logger.info("getAllSubModule method of SubModuleServiceImpl is executed");
		return subModuleRepository.getAllSubModule();
	}

	@Override
	public void deleteSubModule(Integer id) {
		logger.info("deleteSubModule method of SubModuleServiceImpl is executed");
		subModuleRepository.deleteSubModule(id);
	}

	@Override
	public Map<String, Object> geteditById(Integer id) {
		logger.info("geteditById method of SubModuleServiceImpl is executed");
		return subModuleRepository.updateSubModule(id);
	}

}
