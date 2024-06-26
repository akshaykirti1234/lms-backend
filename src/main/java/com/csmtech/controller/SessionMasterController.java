package com.csmtech.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.SessionMasterDto;
import com.csmtech.entity.SessionMaster;
import com.csmtech.exceptions.SessionNotFoundException;
import com.csmtech.service.ScheduleForMasterService;
import com.csmtech.service.SessionMasterService;

@RestController
@CrossOrigin("*")
public class SessionMasterController {

	private static final Logger logger = LoggerFactory.getLogger(SessionMasterController.class);

	@Value("${tempfile.path}")
	private String tempFilePath;

	@Value("${actualfile.path}")
	private String actualFilePath;

	@Autowired
	private SessionMasterService sessionService;
//	@Autowired
//	private SubModuleService subModuleService;
	@Autowired
	private ScheduleForMasterService scheduleForMasterService;

	@PostMapping("/session-master")
	public ResponseEntity<SessionMaster> saveSessionMaster(@RequestBody @Valid SessionMasterDto dto) throws Exception {
		logger.info("saveSessionMaster method of SessionMasterController is executed");
		SessionMaster sm = sessionService.saveSessionMaster(dto);
		List<String> fileUploadList = new ArrayList<>();
		String fileFormat = null;
		String folderName = null;
		fileUploadList.add(sm.getVideo());
		fileUploadList.add(sm.getDocument());
		for (String fileUpload : fileUploadList) {
			if (fileUpload != null && (!fileUpload.equals(""))) {
				int lastDotIndex = fileUpload.lastIndexOf('.');
				if (lastDotIndex != -1) {
					fileFormat = fileUpload.substring(lastDotIndex + 1);
				} else {
					throw new Exception("No file format found");
				}
				if (fileFormat.equals("mp4") || fileFormat.equals("avi") || fileFormat.equals("wmv")
						|| fileFormat.equals("mov") || fileFormat.equals("flv") || fileFormat.equals("mpeg")
						|| fileFormat.equals("mkv") || fileFormat.equals("webm") || fileFormat.equals("3gp")) {
					folderName = "VIDEO";
				} else {
					folderName = "DOCUMENT";
				}
				File folder = new File(actualFilePath + folderName);
				if (!folder.exists()) {
					folder.mkdirs(); // Create the folder if it doesn't exist
				}

				File srcFile = new File(tempFilePath + folderName + "/" + fileUpload);
				File destFile = new File(actualFilePath + folderName + "/" + fileUpload);
				try {
					Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					Files.delete(srcFile.toPath());
				} catch (IOException e) {
					logger.error("error" , e);
				}
			}
		}
		return new ResponseEntity<>(sm , HttpStatus.CREATED);
	}

	 @GetMapping("/session-master")
	    public ResponseEntity<List<Map<String, Object>>> getAllSessionList() {
	        logger.info("getAllSessionList method of SessionMasterController is executed");
	        try {
	            List<Map<String, Object>> sessions = sessionService.getAllSessionMaster();
	            return new ResponseEntity<>(sessions, HttpStatus.OK);
	        } catch (Exception e) {
	            logger.error("Error occurred while fetching session list", e);
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	 }
	 
	@DeleteMapping("/session-master/{id}")
	public ResponseEntity<Map<String, Object>> deleteSessionByid(@PathVariable("id") Integer id) throws SessionNotFoundException {
		logger.info("deleteSessionByid method of SessionMasterController is executed");
		Map<String, Object> response = new HashMap<>();
		sessionService.deleteSessionMasterById(id);
		response.put("status", "Deleted Successfully");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/session-master/{id}")
	public SessionMasterDto getSessionById(@PathVariable("id") Integer id) throws SessionNotFoundException {
		logger.info("getSessionById method of SessionMasterController is executed");
		return sessionService.getSessionMasterById(id);
	}

	@GetMapping("/check-is-last-session/{id}")
	public ResponseEntity<Map<String, Object>> checkIsLastSession(@PathVariable("id") Integer id) {
	    logger.info("checkIsLastSession method of SessionMasterController is executed");
	    Map<String, Object> response = new HashMap<>();
	    try {
	        response.put("isLastSession", sessionService.checkIsLastSession(id));
	        response.put("checkBoxValidation", sessionService.checkBoxValidation(id));
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        logger.error("Error occurred while checking session", e);
	        response.put("error", "An error occurred while processing your request");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@GetMapping("/submodule/{id}")
    public ResponseEntity<?> getScheduleForBySubModId(@PathVariable("id") Integer id) {
        logger.info("getScheduleForBySubModId method of SessionMasterController is executed");
        try {
            List<Map<String, Object>> result = scheduleForMasterService.getScheduleForBySubModuleId(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error occurred while fetching schedule for submodule with id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("/getScheduleBySubModuleId/{submoduleId}")
    public ResponseEntity<?> getScheduleBySubModuleId(@PathVariable Integer submoduleId) {
        logger.info("getScheduleBySubModuleId method of SessionMasterController is executed");
        try {
            return scheduleForMasterService.getScheduleBySubModuleId(submoduleId);
        } catch (Exception e) {
            logger.error("Error occurred while getting schedule by submodule id {}", submoduleId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("/getSessionByScheduleId/{scheduleId}")
    public ResponseEntity<?> getSessionByScheduleId(@PathVariable Integer scheduleId) {
        logger.info("getSessionByScheduleId method of SessionMasterController is executed");
        try {
            return sessionService.getSessionByScheduleId(scheduleId);
        } catch (Exception e) {
            logger.error("Error occurred while getting session by schedule id {}", scheduleId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("/getSessionByscheduleForIdAndUserId/{scheduleId}/{userId}")
    public ResponseEntity<?> getSessionByscheduleForIdAndUserId(@PathVariable Integer scheduleId,
                                                                 @PathVariable Integer userId) {
        logger.info("getSessionByScheduleId method of SessionMasterController is executed");
        try {
            return sessionService.getSessionByscheduleForIdAndUserId(scheduleId, userId);
        } catch (Exception e) {
            logger.error("Error occurred while getting session by schedule id {} and user id {}", scheduleId, userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }
}
