package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.SessionResultDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.entity.SessionAssessmentSetting;
import com.csmtech.entity.SessionMaster;
import com.csmtech.entity.SessionResultMaster;
import com.csmtech.entity.SessionResultStatus;
import com.csmtech.entity.SubModule;
import com.csmtech.repository.SessionAssessmentMasterRepository;
import com.csmtech.repository.SessionAssessmentSettingRepository;
import com.csmtech.repository.SessionResultMasterRepository;
import com.csmtech.repository.SessionResultStatusRepository;
import com.csmtech.service.SessionResultMasterServiceImpl;

@SpringBootTest
public class SessionResultMasterServiceImplTest {
	@Mock
    private SessionResultMasterRepository sessionResultMasterRepository;

    @Mock
    private SessionAssessmentMasterRepository sessionAssessmentMasterRepository;

    @Mock
    private SessionResultStatusRepository sessionResultStatusRepository;

    @Mock
    private SessionAssessmentSettingRepository sessionAssessmentSettingRepository;

    @InjectMocks
    private SessionResultMasterServiceImpl sessionResultMasterService;

    private List<SessionResultDto> sessionResultDtos;
    private SessionAssessmentMaster sessionAssessmentMaster;
    private SessionAssessmentSetting sessionAssessmentSetting;
    private List<SessionResultMaster> sessionResultMasters;

    @BeforeEach
    void setUp() {
        // Initialize DTO list
        sessionResultDtos = new ArrayList<>();
        SessionResultDto dto = new SessionResultDto();
        dto.setQuestionId(1);
        dto.setUserId(1);
        dto.setOption("A");
        sessionResultDtos.add(dto);

        // Initialize SessionAssessmentMaster
        sessionAssessmentMaster = new SessionAssessmentMaster();
        sessionAssessmentMaster.setSessionAssessmentMasterId(1);
        sessionAssessmentMaster.setAnswer("A");
        // Mock the nested objects and IDs
        SessionMaster sessionMaster = new SessionMaster();
        sessionMaster.setSessionId(1);
        sessionAssessmentMaster.setSessionMaster(sessionMaster);

        ModuleMaster moduleMaster = new ModuleMaster();
        moduleMaster.setModuleId(1);
        sessionAssessmentMaster.setModuleMaster(moduleMaster);

        SubModule subModule = new SubModule();
        subModule.setSubmoduleId(1);
        sessionAssessmentMaster.setSubModule(subModule);

        ScheduleForMaster scheduleForMaster = new ScheduleForMaster();
        scheduleForMaster.setScheduleForId(1);
        sessionAssessmentMaster.setScheduleForMaster(scheduleForMaster);

        // Initialize SessionAssessmentSetting
        sessionAssessmentSetting = new SessionAssessmentSetting();
        sessionAssessmentSetting.setPassingPercentage(60.0);

        // Initialize SessionResultMaster list
        sessionResultMasters = new ArrayList<>();
        SessionResultMaster sessionResultMaster = new SessionResultMaster();
        sessionResultMaster.setResultStatus(true);
        sessionResultMasters.add(sessionResultMaster);
    }

    @Test
    void testSaveSessionResult() {
        // Mocking repository methods
        when(sessionAssessmentMasterRepository.findById(anyInt())).thenReturn(Optional.of(sessionAssessmentMaster));
        doNothing().when(sessionResultMasterRepository).deleteResultBySessionIdAndUserId(anyInt(), anyInt());
        when(sessionResultMasterRepository.save(any(SessionResultMaster.class))).thenReturn(new SessionResultMaster());
        when(sessionResultMasterRepository.getResultBySessionIdAndUserID(anyInt(), anyInt())).thenReturn(sessionResultMasters);
        when(sessionAssessmentSettingRepository.findBySessionId(anyInt())).thenReturn(sessionAssessmentSetting);
        when(sessionResultStatusRepository.save(any(SessionResultStatus.class))).thenReturn(new SessionResultStatus());

        // Call the method under test
        ResponseEntity<?> response = sessionResultMasterService.saveSessionResult(sessionResultDtos);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertTrue(responseBody.containsKey("percentage"));
        assertTrue(responseBody.containsKey("passingPercentage"));

        // Verify interactions with mocks
        verify(sessionAssessmentMasterRepository, times(2)).findById(anyInt());
        verify(sessionResultMasterRepository, times(1)).deleteResultBySessionIdAndUserId(anyInt(), anyInt());
        verify(sessionResultMasterRepository, times(sessionResultDtos.size())).save(any(SessionResultMaster.class));
        verify(sessionResultStatusRepository, times(1)).save(any(SessionResultStatus.class));
    }

    @Test
    void testSaveSessionResult_AssessmentMasterNotFound() {
        // Mocking repository method to return empty
        when(sessionAssessmentMasterRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Expecting RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sessionResultMasterService.saveSessionResult(sessionResultDtos);
        });

        assertEquals("Session Assessment Master not found", exception.getMessage());
    }
}
