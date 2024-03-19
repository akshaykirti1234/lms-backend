package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.SubModuleDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.service.ModuleMasterService;
import com.csmtech.service.SubModuleService;

@CrossOrigin
@RestController
public class SubModuleController {

	@Autowired
	private ModuleMasterService moduleMasterService;
	@Autowired
	private SubModuleService subModuleService;

	@GetMapping("/moduleList")
	public ResponseEntity<?> getModuleList() {
		List<ModuleMaster> moduleList = moduleMasterService.getModuleMaster();
		return ResponseEntity.ok().body(moduleList);
	}

	@PostMapping("saveSubModule")
	public ResponseEntity<Map<String, Object>> saveSubModuleMaster(@RequestBody SubModuleDto subModuleDto) {
		Map<String, Object> result = subModuleService.saveSubModule(subModuleDto);
		return ResponseEntity.ok(result);
	}

	@GetMapping("viewSubModule")
	public ResponseEntity<List<Map<String, Object>>> getAllSubModule() {
		List<Map<String, Object>> subModuleList = subModuleService.getAllSubModule();
		return ResponseEntity.ok().body(subModuleList);
	}

	@GetMapping("delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteSubModule(@PathVariable("id") Integer id) {
		Map<String, Object> response = new HashMap<>();
		subModuleService.deleteSubModule(id);
		response.put("status", "deleted");
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("editSubModule/{id}")
	public ResponseEntity<Map<String, Object>> geteditById(@PathVariable("id") Integer id) {
		Map<String, Object> update = subModuleService.geteditById(id);
		return ResponseEntity.ok().body(update);
	}

}
