package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.AssessmentSettingDto;
import com.csmtech.dto.AssessmentSettingScheduleDto;
import com.csmtech.entity.AssessmentSetting;
import com.csmtech.service.AssessmentSettingService;

public class AssessmentSettingControllerTest {

    @Mock
    private AssessmentSettingService assessmentSettingService;

    @InjectMocks
    private AssessmentSettingController assessmentSettingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetScheduleforAssessmentSetting() {
        List<Map<String, Object>> mockScheduleList = new ArrayList<>();
        when(assessmentSettingService.getScheduleforAssessmentSetting(anyInt())).thenReturn(mockScheduleList);

        ResponseEntity<List<Map<String, Object>>> response = assessmentSettingController.getScheduleforAssessmentSetting(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockScheduleList, response.getBody());
    }

    @Test
    public void testSaveAssessmentSetting() {
        AssessmentSettingDto mockDto = new AssessmentSettingDto();
        AssessmentSetting mockAssessmentSetting = new AssessmentSetting();
        when(assessmentSettingService.saveAssessmentSetting(any(AssessmentSettingDto.class))).thenReturn(mockAssessmentSetting);

        ResponseEntity<AssessmentSetting> response = assessmentSettingController.saveAssessmentSetting(mockDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAssessmentSetting, response.getBody());
    }

    @Test
    public void testGetAssessmentSetting() {
        List<Map<String, Object>> mockAssessmentSettingList = new ArrayList<>();
        when(assessmentSettingService.getAssessmentSetting()).thenReturn(mockAssessmentSettingList);

        ResponseEntity<List<Map<String, Object>>> response = assessmentSettingController.getAssessmentSetting();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAssessmentSettingList, response.getBody());
    }

    @Test
    public void testGetAssessmentSettingById() {
        Map<String, Object> mockAssessmentSetting = new HashMap<>();
        when(assessmentSettingService.getAssessmentSettingById(anyInt())).thenReturn(mockAssessmentSetting);

        ResponseEntity<Map<String, Object>> response = assessmentSettingController.getAssessmentSettingById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAssessmentSetting, response.getBody());
    }

    @Test
    public void testUpdateAssessmentSetting() {
        AssessmentSettingScheduleDto mockDto = new AssessmentSettingScheduleDto();
        mockDto.setNumberOfQuestions(10);
        mockDto.setPassingPercentage(60.0);

        ResponseEntity<Map<String, Object>> response = assessmentSettingController.updateAssessmentSetting(1, mockDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assertEquals(200, responseBody.get("status"));
        assertEquals("AssessmentSetting updated successfully", responseBody.get("Updated"));
    }

    @Test
    public void testDeleteAssessmentSetting() {
        ResponseEntity<Map<String, Object>> response = assessmentSettingController.deleteAssessmentSetting(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = response.getBody();
        assertEquals(200, responseBody.get("status"));
        assertEquals("AssessmentSetting successfully deleted", responseBody.get("Deleted"));

        verify(assessmentSettingService).deleteAssessmentSetting(1);
    }
}
