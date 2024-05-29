package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.SessionResultDto;
import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.entity.SessionAssessmentSetting;
import com.csmtech.entity.SessionResultMaster;
import com.csmtech.repository.SessionAssessmentMasterRepository;
import com.csmtech.repository.SessionAssessmentSettingRepository;
import com.csmtech.repository.SessionResultMasterRepository;
import com.csmtech.repository.SessionResultStatusRepository;
import com.csmtech.service.SessionResultMasterServiceImpl;

@SpringBootTest
public class SessionResultMasterServiceImplTest {

    @InjectMocks
    private SessionResultMasterServiceImpl sessionResultService;

    @Mock
    private SessionResultMasterRepository sessionResultRepo;

    @Mock
    private SessionAssessmentMasterRepository sessionAssessmentRepo;

    @Mock
    private SessionResultStatusRepository sessionResultStatusRepo;

    @Mock
    private SessionAssessmentSettingRepository sessionAssessmentSettingRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSessionResult_Success() {
        // Mock data
        SessionResultDto sessionResultDto1 = new SessionResultDto();
        sessionResultDto1.setUserId(1);
        sessionResultDto1.setQuestionId(1);
        sessionResultDto1.setOption("A");

        SessionResultDto sessionResultDto2 = new SessionResultDto();
        sessionResultDto2.setUserId(1);
        sessionResultDto2.setQuestionId(2);
        sessionResultDto2.setOption("B");

        List<SessionResultDto> responsePayload = Arrays.asList(sessionResultDto1, sessionResultDto2);

        SessionAssessmentMaster sessionAssessmentMaster1 = new SessionAssessmentMaster();
        // Set up more mock objects as needed...

        // Mock repository methods
        when(sessionAssessmentRepo.findById(1)).thenReturn(Optional.of(sessionAssessmentMaster1));
        // Mock more repository methods as needed...

        // Mock repository save method
        when(sessionResultRepo.save(any(SessionResultMaster.class))).thenReturn(new SessionResultMaster());

        // Mock repository getResultBySessionIdAndUserID method
        when(sessionResultRepo.getResultBySessionIdAndUserID(anyInt(), anyInt())).thenReturn(Collections.emptyList());

        // Mock repository findBySessionId method
        SessionAssessmentSetting sessionAssessmentSetting = new SessionAssessmentSetting();
        sessionAssessmentSetting.setPassingPercentage(70.0);
        when(sessionAssessmentSettingRepo.findBySessionId(anyInt())).thenReturn(sessionAssessmentSetting);

        // Test method
        ResponseEntity<?> result = sessionResultService.saveSessionResult(responsePayload);

        // Assertions
        assertEquals(HttpStatus.OK, result.getStatusCode());
        // Add more assertions as needed...
    }


}
