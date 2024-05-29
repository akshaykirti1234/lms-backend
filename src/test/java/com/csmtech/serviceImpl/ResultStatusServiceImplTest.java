package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.csmtech.entity.ResultStatus;
import com.csmtech.repository.ResultStatusRepository;
import com.csmtech.service.ResultStatusServiceImpl;

public class ResultStatusServiceImplTest {

	@Mock
    private ResultStatusRepository resultStatusRepository;
    
    @InjectMocks
    private ResultStatusServiceImpl resultStatusService;

    private ResultStatus resultStatus;
    private List<ResultStatus> resultStatusList;

    @BeforeEach
    void setUp() {
        resultStatus = new ResultStatus();
        resultStatus.setResultStatusId(1);
        resultStatusList = Arrays.asList(resultStatus);

    }
    @Test
    void testGetFinalResultByScheduleIdUserId() {
        Integer scheduleForId = 1;
        Integer userId = 1;

        when(resultStatusRepository.getFinalResultByScheduleIdUserId(scheduleForId, userId)).thenReturn(resultStatusList);

        List<ResultStatus> result = resultStatusService.getFinalResultByScheduleIdUserId(scheduleForId, userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(resultStatusRepository, times(1)).getResultStatusByScheduleIdUserId(scheduleForId, userId);
    }
}
