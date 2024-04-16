package com.csmtech.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.csmtech.dto.SubModuleDto;
import com.csmtech.entity.SubModule;

public interface SubModuleService {

	ResponseEntity<?> getSubmoduleByModuleId(Integer moduleId);

	List<SubModule> getAllSubModules();

	Map<String, Object> saveSubModule(SubModuleDto subModuleDto);

	List<Map<String, Object>> getAllSubModule();

	void deleteSubModule(Integer id);

	Map<String, Object> geteditById(Integer id);

}
