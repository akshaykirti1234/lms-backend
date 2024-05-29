package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import com.csmtech.entity.ResultStatus;
import com.csmtech.repository.ResultStatusRepository;
import com.csmtech.service.ResultStatusServiceImpl;

// Import your classes and dependencies here

public class ResultStatusServiceImplTest {

    @InjectMocks
    private ResultStatusServiceImpl resultStatusService;

    @Mock
    private ResultStatusRepository resultStatusRepository;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFinalResultByScheduleIdUserId() {
        // Mock data
        Integer scheduleForId = 1;
        Integer userId = 1;
        List<ResultStatus> expectedResult = new ArrayList<>(); // Fill with expected data

        // Mock repository method
        when(resultStatusRepository.getFinalResultByScheduleIdUserId(scheduleForId, userId))
            .thenReturn(expectedResult);

        // Call the service method
        List<ResultStatus> actualResult = resultStatusService.getFinalResultByScheduleIdUserId(scheduleForId, userId);

        // Assertions
        assertEquals(expectedResult, actualResult);
        verify(resultStatusRepository, times(1)).getFinalResultByScheduleIdUserId(scheduleForId, userId);
    }

    @Test
    public void testGetResultStatusByScheduleIdUserId() {
        // Mock data
        Integer scheduleForId = 1;
        Integer userId = 1;
        ResultStatus expectedResult = new ResultStatus(); // Fill with expected data

        // Mock repository method
        when(resultStatusRepository.getResultStatusByScheduleIdUserId(scheduleForId, userId))
            .thenReturn(expectedResult);

        // Call the service method
        ResultStatus actualResult = resultStatusService.getResultStatusByScheduleIdUserId(scheduleForId, userId);

        // Assertions
        assertEquals(expectedResult, actualResult);
        verify(resultStatusRepository, times(1)).getResultStatusByScheduleIdUserId(scheduleForId, userId);
    }
}
