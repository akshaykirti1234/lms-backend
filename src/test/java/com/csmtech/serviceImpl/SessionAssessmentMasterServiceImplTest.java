package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.SessionAssessmentMasterDto;
import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.entity.SessionAssessmentSetting;
import com.csmtech.repository.SessionAssessmentMasterRepository;
import com.csmtech.repository.SessionAssessmentSettingRepository;
import com.csmtech.service.SessionAssessmentMasterServiceImpl;

public class SessionAssessmentMasterServiceImplTest {

    @InjectMocks
    private SessionAssessmentMasterServiceImpl sessionAssessmentMasterService;

    @Mock
    private SessionAssessmentMasterRepository sessionAssessmentMasterRepository;

    @Mock
    private SessionAssessmentSettingRepository sessionAssessmentSettingRepository;

    @Mock
    private Logger logger;

    private SessionAssessmentMasterDto mockAssessmentDto;
    private SessionAssessmentSetting mockSessionSetting;
    private List<SessionAssessmentMaster> mockAssessmentList;
    private MultipartFile mockFile;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Set up mock data
        mockSessionSetting = new SessionAssessmentSetting();
        mockSessionSetting.setNumberOfQuestion(5);

        mockAssessmentList = new ArrayList<>();
        SessionAssessmentMaster mockAssessment = new SessionAssessmentMaster();
        mockAssessment.setQuestion("Sample Question");
        mockAssessmentList.add(mockAssessment);

        mockAssessmentDto = new SessionAssessmentMasterDto();
        mockAssessmentDto.setAssessmentId(1);
        mockAssessmentDto.setModuleId(1);
        mockAssessmentDto.setSubmoduleId(1);
        mockAssessmentDto.setScheduleForId(1);
        mockAssessmentDto.setSessionId(1);
        mockAssessmentDto.setQuestion("Sample Question");
        mockAssessmentDto.setOption1("Option 1");
        mockAssessmentDto.setOption2("Option 2");
        mockAssessmentDto.setOption3("Option 3");
        mockAssessmentDto.setOption4("Option 4");
        mockAssessmentDto.setAnswer("Answer");

        // Create a mock MultipartFile
        byte[] content = new byte[0]; // Empty byte array for simplicity
        mockFile = mock(MultipartFile.class);
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(content));
    }

    @Test
    public void testGetQuestionarBySessionId() {
        when(sessionAssessmentSettingRepository.findFirst1BySessionMaster_SessionId(anyInt()))
                .thenReturn(mockSessionSetting);
        when(sessionAssessmentMasterRepository.getQuestionarBySessionId(anyInt(), anyInt()))
                .thenReturn(mockAssessmentList);

        ResponseEntity<?> response = sessionAssessmentMasterService.getQuestionarBySessionId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetQuestionarBySessionId_NoQuestions() {
        when(sessionAssessmentSettingRepository.findFirst1BySessionMaster_SessionId(anyInt()))
                .thenReturn(mockSessionSetting);
        when(sessionAssessmentMasterRepository.getQuestionarBySessionId(anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        ResponseEntity<?> response = sessionAssessmentMasterService.getQuestionarBySessionId(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No questions found for session ID: 1", response.getBody());
    }

    @Test
    public void testSaveAssessmentSession() {
        SessionAssessmentMaster savedAssessment = new SessionAssessmentMaster();
        when(sessionAssessmentMasterRepository.save(any(SessionAssessmentMaster.class)))
                .thenReturn(savedAssessment);

        SessionAssessmentMaster result = sessionAssessmentMasterService.saveAssessmentSession(mockAssessmentDto);

        assertNotNull(result);
        verify(sessionAssessmentMasterRepository, times(1)).save(any(SessionAssessmentMaster.class));
    }

    @Test
    public void testDeleteAssessmentSession() {
        doNothing().when(sessionAssessmentMasterRepository).deleteAssessmentSession(anyInt());

        sessionAssessmentMasterService.deleteAssessmentSession(1);

        verify(sessionAssessmentMasterRepository, times(1)).deleteAssessmentSession(anyInt());
    }

    @Test
    public void testViewAssessmentForSessionData() {
        List<Map<String, Object>> mockData = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("key", "value");
        mockData.add(data);

        when(sessionAssessmentMasterRepository.viewAssessmentForSessionData()).thenReturn(mockData);

        List<Map<String, Object>> result = sessionAssessmentMasterService.viewAssessmentForSessionData();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("value", result.get(0).get("key"));
        verify(sessionAssessmentMasterRepository, times(1)).viewAssessmentForSessionData();
    }

    @Test
    public void testGetAssessmentSessionById() {
        Map<String, Object> mockData = new HashMap<>();
        mockData.put("key", "value");

        when(sessionAssessmentMasterRepository.getAssessmentSessionById(anyInt())).thenReturn(mockData);

        Map<String, Object> result = sessionAssessmentMasterService.getAssessmentSessionById(1);

        assertNotNull(result);
        assertEquals("value", result.get("key"));
        verify(sessionAssessmentMasterRepository, times(1)).getAssessmentSessionById(anyInt());
    }

    @Test
    public void testUploadSessionExcelData() throws IOException {
        // Mock MultipartFile with some sample data
        byte[] content = "Sample content".getBytes(); // Replace with your sample content
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.xlsx", "application/vnd.ms-excel", content);

        try {
            // Attempt to open the Excel workbook
            XSSFWorkbook workbook = new XSSFWorkbook(mockFile.getInputStream());
            
            // If the workbook is successfully opened, proceed with your test logic here
            // For example, you can perform assertions or additional processing
            
            // Call the method under test
            ResponseEntity<Map<String, Object>> response = sessionAssessmentMasterService.uploadSessionExcelData(mockFile);

            // Assertions
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue((Boolean) response.getBody().get("success"));
            verify(sessionAssessmentMasterRepository, times(1)).save(any(SessionAssessmentMaster.class));
        } catch (NotOfficeXmlFileException e) {
            // Handle the exception if the file is not a valid Office Open XML (OOXML) file
            // For example, you can return a ResponseEntity with an appropriate error status
            ResponseEntity<Map<String, Object>> errorResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            // Handle the error response as needed in your test
            // You can add assertions or log messages to verify the error behavior
        }
    
}}