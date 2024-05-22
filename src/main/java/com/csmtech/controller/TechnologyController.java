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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.TechnologyDto;
import com.csmtech.entity.Technology;
import com.csmtech.service.TechnologyService;

@RestController
@CrossOrigin("*")
public class TechnologyController {

    private static final Logger logger = LoggerFactory.getLogger(TechnologyController.class);

    @Autowired
    private TechnologyService technologyService;

    @PostMapping("technology")
    public ResponseEntity<?> saveTechnology(@RequestBody TechnologyDto technologyDto) {
        logger.info("saveTechnology method of TechnologyController is executed");
        try {
            Technology tech = technologyService.saveTechnology(technologyDto);
            return ResponseEntity.ok().body(tech);
        } catch (Exception e) {
            logger.error("Error occurred while saving technology", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("/alltechnology")
    public ResponseEntity<?> getAllTechnology() {
        logger.info("getAllTechnology method of TechnologyController is executed");
        try {
            List<Technology> techList = technologyService.getAllTechnologies();
            return ResponseEntity.ok().body(techList);
        } catch (Exception e) {
            logger.error("Error occurred while fetching all technologies", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @DeleteMapping("deleteTech/{tecId}")
    public ResponseEntity<?> deleteTechnology(@PathVariable("tecId") Integer tecId) {
        logger.info("deleteTechnology method of TechnologyController is executed");
        try {
            Map<String, Object> response = new HashMap<>();
            technologyService.deleteTechnology(tecId);
            response.put("status", "Deleted");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error occurred while deleting technology with ID {}", tecId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("editTech/{tecId}")
    public ResponseEntity<?> editTechnology(@PathVariable("tecId") Integer tecId) {
        logger.info("editTechnology method of TechnologyController is executed");
        try {
            Technology update = technologyService.editTechnology(tecId);
            return ResponseEntity.ok().body(update);
        } catch (Exception e) {
            logger.error("Error occurred while editing technology with ID {}", tecId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }
}
