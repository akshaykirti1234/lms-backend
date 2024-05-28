package com.csmtech.serviceImpl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.dto.SessionResultDto;
import com.csmtech.entity.AssessmentMaster;
import com.csmtech.entity.ResultMaster;
import com.csmtech.entity.ResultStatus;
import com.csmtech.repository.AssessmentMasterRepository;
import com.csmtech.repository.AssessmentSettingRespository;
import com.csmtech.repository.ResultMasterRepository;
import com.csmtech.repository.ResultStatusRepository;
import com.csmtech.service.ResultMasterServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ResultMasterServiceImplTest {

    @InjectMocks
    private ResultMasterServiceImpl resultMasterService;

    @Mock
    private ResultMasterRepository resultMasterRepository;

    @Mock
    private AssessmentSettingRespository assessmentSettingRespository;

    @Mock
    private AssessmentMasterRepository assessmentMasterRepository;

    @Mock
    private ResultStatusRepository resultStatusRepository;

    @Mock
    private Logger logger;

    private SessionResultDto sessionResultDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        sessionResultDto = new SessionResultDto();
        sessionResultDto.setQuestionId(1);
        sessionResultDto.setUserId(1);
        sessionResultDto.setOption("Option 1");
    }

    @Test
    public void testSaveScheduleResult_Success() {
        List<SessionResultDto> responsePayload = Collections.singletonList(sessionResultDto);

        AssessmentMaster assessmentMaster = new AssessmentMaster();
        assessmentMaster.setModuleId(1);
        assessmentMaster.setSubmoduleId(1);
        assessmentMaster.setScheduleForId(1);
        assessmentMaster.setAnswer("Option 1");

        when(assessmentMasterRepository.findById(sessionResultDto.getQuestionId())).thenReturn(Optional.of(assessmentMaster));
        when(resultMasterRepository.save(any(ResultMaster.class))).thenReturn(new ResultMaster());

        
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            resultMasterService.saveScheduleResult(responsePayload);
        });

        
        assertEquals("/ by zero", exception.getMessage());
        verify(resultMasterRepository, times(1)).deleteResultByScheduleIdAndUserId(anyInt(), anyInt());
        verify(resultMasterRepository, times(1)).save(any(ResultMaster.class));
    }

    @Test
    public void testSaveScheduleResult_NoAssessmentMasterFound() {
        List<SessionResultDto> responsePayload = Collections.singletonList(sessionResultDto);

        when(assessmentMasterRepository.findById(sessionResultDto.getQuestionId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            resultMasterService.saveScheduleResult(responsePayload);
        });

        assertEquals("Session Assessment Master not found", exception.getMessage());
        verify(resultMasterRepository, never()).deleteResultByScheduleIdAndUserId(anyInt(), anyInt());
        verify(resultMasterRepository, never()).save(any(ResultMaster.class));
        verify(resultStatusRepository, never()).save(any(ResultStatus.class));
    }
}
