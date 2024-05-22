package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private static final Logger logger = LoggerFactory.getLogger(SubModuleController.class);

    @Autowired
    private ModuleMasterService moduleMasterService;
    @Autowired
    private SubModuleService subModuleService;

    @GetMapping("/moduleList")
    public ResponseEntity<?> getModuleList() {
        logger.info("getModuleList method of SubModuleController is executed");
        try {
            List<ModuleMaster> moduleList = moduleMasterService.getModuleMaster();
            return ResponseEntity.ok().body(moduleList);
        } catch (Exception e) {
            logger.error("Error occurred while fetching module list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @PostMapping("saveSubModule")
    public ResponseEntity<?> saveSubModuleMaster(@RequestBody SubModuleDto subModuleDto) {
        logger.info("saveSubModuleMaster method of SubModuleController is executed");
        try {
            Map<String, Object> result = subModuleService.saveSubModule(subModuleDto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error occurred while saving sub-module", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("viewSubModule")
    public ResponseEntity<?> getAllSubModule() {
        logger.info("getAllSubModule method of SubModuleController is executed");
        try {
            List<Map<String, Object>> subModuleList = subModuleService.getAllSubModule();
            return ResponseEntity.ok().body(subModuleList);
        } catch (Exception e) {
            logger.error("Error occurred while fetching all sub-modules", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteSubModule(@PathVariable("id") Integer id) {
        logger.info("deleteSubModule method of SubModuleController is executed");
        try {
            Map<String, Object> response = new HashMap<>();
            subModuleService.deleteSubModule(id);
            response.put("status", "deleted");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error occurred while deleting sub-module with id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("editSubModule/{id}")
    public ResponseEntity<?> geteditById(@PathVariable("id") Integer id) {
        logger.info("geteditById method of SubModuleController is executed");
        try {
            Map<String, Object> update = subModuleService.geteditById(id);
            return ResponseEntity.ok().body(update);
        } catch (Exception e) {
            logger.error("Error occurred while fetching sub-module with id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("/getSubModuleByModuleId/{moduleId}")
    public ResponseEntity<?> getSubModuleByModuleId(@PathVariable Integer moduleId) {
        logger.info("getSubModuleByModuleId method of SubModuleController is executed");
        try {
            ResponseEntity<?> response = subModuleService.getSubmoduleByModuleId(moduleId);
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while fetching sub-modules for module with id {}", moduleId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }
}

