package com.csmtech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Autowired
	private SubModuleRepository subModuleRepository;

	@Override
	public ResponseEntity<?> getSubmoduleById(Integer moduleId) {
		List<SubModule> submoduleList = subModuleRepository.getSubmoduleById(moduleId);
		return new ResponseEntity<>(submoduleList, HttpStatus.OK);
	}

	@Override
	public List<SubModule> getAllSubModules() {
		return subModuleRepository.getAllSubModules();
	}

	@Override
	public Map<String, Object> saveSubModule(SubModuleDto subModuleDto) {
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
			e.printStackTrace();
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Something went wrong");
		}
		return response;
	}

	@Override
	public List<Map<String, Object>> getAllSubModule() {
		return subModuleRepository.getAllSubModule();
	}

	@Override
	public void deleteSubModule(Integer id) {
		subModuleRepository.deleteSubModule(id);
	}

	@Override
	public Map<String, Object> geteditById(Integer id) {
		return subModuleRepository.updateSubModule(id);
	}

}
