package com.csmtech.serviceImpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.dto.AssessmentSettingDto;
import com.csmtech.dto.AssessmentSettingScheduleDto;
import com.csmtech.entity.AssessmentSetting;
import com.csmtech.repository.AssessmentSettingRespository;
import com.csmtech.service.AssessmentSettingServiceImpl;

import org.slf4j.Logger;

import java.util.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AssessmentSettingServiceImplTest {

    @InjectMocks
    private AssessmentSettingServiceImpl assessmentSettingService;

    @Mock
    private AssessmentSettingRespository assessmentSettingRepository;  

    @Mock
    private Logger logger;

    private AssessmentSettingDto assessmentSettingDto;
    private List<AssessmentSettingScheduleDto> scheduleDtoList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        scheduleDtoList = new ArrayList<>();
        AssessmentSettingScheduleDto scheduleDto = new AssessmentSettingScheduleDto();
        scheduleDto.setScheduleForId(1);
        scheduleDto.setNumberOfQuestions(10);
        scheduleDto.setPassingPercentage(60.0);
        scheduleDtoList.add(scheduleDto);

        assessmentSettingDto = new AssessmentSettingDto();
        assessmentSettingDto.setModule(1);
        assessmentSettingDto.setSubModule(1);
        assessmentSettingDto.setScheduleWiseList(scheduleDtoList);
    }

    @Test
    public void testSaveAssessmentSetting_Success() {
        AssessmentSetting assessmentSetting = new AssessmentSetting();
        when(assessmentSettingRepository.save(any(AssessmentSetting.class))).thenReturn(assessmentSetting);

        AssessmentSetting result = assessmentSettingService.saveAssessmentSetting(assessmentSettingDto);
        assertNotNull(result);
        verify(assessmentSettingRepository, times(1)).save(any(AssessmentSetting.class));
    }

    @Test
    public void testSaveAssessmentSetting_Failure() {
        doThrow(new RuntimeException("Database Error")).when(assessmentSettingRepository).save(any(AssessmentSetting.class));
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            assessmentSettingService.saveAssessmentSetting(assessmentSettingDto);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(assessmentSettingRepository, times(1)).save(any(AssessmentSetting.class));
    }

    @Test
    public void testDeleteAssessmentSetting() {
        Integer assessmentSettingId = 1;
        assessmentSettingService.deleteAssessmentSetting(assessmentSettingId);
        verify(assessmentSettingRepository, times(1)).deleteAssessmentSetting(assessmentSettingId);
    }

    @Test
    public void testGetAssessmentSetting() {
        List<Map<String, Object>> assessmentSettingList = new ArrayList<>();
        Map<String, Object> assessmentSetting = new HashMap<>();
        assessmentSetting.put("assessmentSettingId", 1);
        assessmentSetting.put("moduleId", 1);
        assessmentSettingList.add(assessmentSetting);
        
        when(assessmentSettingRepository.getAssessmentSetting()).thenReturn(assessmentSettingList);

        List<Map<String, Object>> result = assessmentSettingService.getAssessmentSetting();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).get("moduleId"));
        verify(assessmentSettingRepository, times(1)).getAssessmentSetting();
    }

    @Test
    public void testGetAssessmentSettingById() {
        Integer assessmentSettingId = 1;
        Map<String, Object> assessmentSetting = new HashMap<>();
        assessmentSetting.put("assessmentSettingId", assessmentSettingId);
        
        when(assessmentSettingRepository.getAssessmentSettingById(assessmentSettingId)).thenReturn(assessmentSetting);

        Map<String, Object> result = assessmentSettingService.getAssessmentSettingById(assessmentSettingId);
        assertNotNull(result);
        assertEquals(assessmentSettingId, result.get("assessmentSettingId"));
        verify(assessmentSettingRepository, times(1)).getAssessmentSettingById(assessmentSettingId);
    }

    @Test
    public void testUpdateAssessmentSetting() {
        Integer assessmentSettingId = 1;
        Integer noOfQuestions = 10;
        Double passingPercentage = 60.0;

        doNothing().when(assessmentSettingRepository).updateAssessmentSetting(assessmentSettingId, noOfQuestions, passingPercentage);
        
        assessmentSettingService.updateAssessmentSetting(assessmentSettingId, noOfQuestions, passingPercentage);
        verify(assessmentSettingRepository, times(1)).updateAssessmentSetting(assessmentSettingId, noOfQuestions, passingPercentage);
    }

    @Test
    public void testGetScheduleforAssessmentSetting() {
        Integer submoduleId = 1;
        List<Map<String, Object>> scheduleList = new ArrayList<>();
        Map<String, Object> schedule = new HashMap<>();
        schedule.put("submoduleId", submoduleId);
        scheduleList.add(schedule);
        
        when(assessmentSettingRepository.getScheduleforAssessmentSetting(submoduleId)).thenReturn(scheduleList);

        List<Map<String, Object>> result = assessmentSettingService.getScheduleforAssessmentSetting(submoduleId);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(submoduleId, result.get(0).get("submoduleId"));
        verify(assessmentSettingRepository, times(1)).getScheduleforAssessmentSetting(submoduleId);
    }
}
