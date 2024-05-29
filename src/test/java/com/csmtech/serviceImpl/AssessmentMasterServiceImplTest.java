package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.AssessmentMasterDto;
import com.csmtech.entity.AssessmentMaster;
import com.csmtech.entity.AssessmentSetting;
import com.csmtech.repository.AssessmentMasterRepository;
import com.csmtech.repository.AssessmentSettingRespository;
import com.csmtech.repository.ScheduleForMasterRepository;
import com.csmtech.repository.SessionAssessmentMasterRepository;
import com.csmtech.service.AssessmentMasterServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AssessmentMasterServiceImplTest {

    @InjectMocks
    private AssessmentMasterServiceImpl assessmentMasterService;

    @Mock
    private AssessmentMasterRepository assessmentMasterRepository;

    @Mock
    private ScheduleForMasterRepository scheduleForMasterRepository;

    @Mock
    private SessionAssessmentMasterRepository sessionAssessmentMasterRepository;

    @Mock
    private AssessmentSettingRespository assessmentSettingRespository;

    @Mock
    private Logger logger;

    private AssessmentMasterDto assessmentDto;
    private AssessmentMaster assessmentMaster;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        assessmentDto = new AssessmentMasterDto();
        assessmentDto.setAssessmentId(1);
        assessmentDto.setModuleId(1);
        assessmentDto.setSubmoduleId(1);
        assessmentDto.setScheduleForId(1);
        assessmentDto.setQuestion("Sample Question");
        assessmentDto.setOption1("Option 1");
        assessmentDto.setOption2("Option 2");
        assessmentDto.setOption3("Option 3");
        assessmentDto.setOption4("Option 4");
        assessmentDto.setAnswer("Answer");

        assessmentMaster = new AssessmentMaster();
        assessmentMaster.setAssessmentId(1);
        assessmentMaster.setModuleId(1);
        assessmentMaster.setSubmoduleId(1);
        assessmentMaster.setScheduleForId(1);
        assessmentMaster.setQuestion("Sample Question");
        assessmentMaster.setOption1("Option 1");
        assessmentMaster.setOption2("Option 2");
        assessmentMaster.setOption3("Option 3");
        assessmentMaster.setOption4("Option 4");
        assessmentMaster.setAnswer("Answer");
        assessmentMaster.setCreatedBy(1);
        assessmentMaster.setUpdatedBy(1);
    }

    @Test
    public void testSaveAssessment_Success() {
        when(assessmentMasterRepository.save(any(AssessmentMaster.class))).thenReturn(assessmentMaster);

        AssessmentMaster result = assessmentMasterService.saveAssessment(assessmentDto);

        assertNotNull(result);
        assertEquals(assessmentDto.getAssessmentId(), result.getAssessmentId());
        verify(assessmentMasterRepository, times(1)).save(any(AssessmentMaster.class));
    }

    @Test
    public void testSaveAssessment_Failure() {
        when(assessmentMasterRepository.save(any(AssessmentMaster.class))).thenThrow(new RuntimeException("Database Error"));

        assertThrows(RuntimeException.class, () -> {
            assessmentMasterService.saveAssessment(assessmentDto);
        });
        verify(assessmentMasterRepository, times(1)).save(any(AssessmentMaster.class));
    }

    @Test
    public void testViewAssessmentData() {
        List<Map<String, Object>> assessmentData = new ArrayList<>();
        Map<String, Object> assessment = new HashMap<>();
        assessment.put("assessmentId", 1);
        assessment.put("question", "Sample Question");
        assessmentData.add(assessment);

        when(assessmentMasterRepository.getAllAssesmentdata()).thenReturn(assessmentData);

        List<Map<String, Object>> result = assessmentMasterService.viewAssessmentData();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample Question", result.get(0).get("question"));
        verify(assessmentMasterRepository, times(1)).getAllAssesmentdata();
    }

    @Test
    public void testDeleteAssessment() {
        Integer assessmentId = 1;
        assessmentMasterService.deleteAssessment(assessmentId);
        verify(assessmentMasterRepository, times(1)).deleteAssessment(assessmentId);
    }

    @Test
    public void testGetAssessmentById() {
        Integer assessmentId = 1;
        Map<String, Object> assessment = new HashMap<>();
        assessment.put("assessmentId", assessmentId);
        assessment.put("question", "Sample Question");

        when(assessmentMasterRepository.updateAssessment(assessmentId)).thenReturn(assessment);

        Map<String, Object> result = assessmentMasterService.getAssessmentById(assessmentId);

        assertNotNull(result);
        assertEquals(assessmentId, result.get("assessmentId"));
        assertEquals("Sample Question", result.get("question"));
        verify(assessmentMasterRepository, times(1)).updateAssessment(assessmentId);
    }

    @Test
    public void testRetriveModuleTypeList() {
        List<Map<String, Object>> moduleTypeList = new ArrayList<>();
        Map<String, Object> module = new HashMap<>();
        module.put("moduleId", 1);
        module.put("moduleName", "Sample Module");
        moduleTypeList.add(module);

        when(assessmentMasterRepository.retriveModuleTypeList()).thenReturn(moduleTypeList);

        List<Map<String, Object>> result = assessmentMasterService.retriveModuleTypeList();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample Module", result.get(0).get("moduleName"));
        verify(assessmentMasterRepository, times(1)).retriveModuleTypeList();
    }

    @Test
    public void testRetriveSubModuleList() {
        List<Map<String, Object>> subModuleList = new ArrayList<>();
        Map<String, Object> subModule = new HashMap<>();
        subModule.put("submoduleId", 1);
        subModule.put("submoduleName", "Sample Submodule");
        subModuleList.add(subModule);

        when(assessmentMasterRepository.retriveSubModuleList()).thenReturn(subModuleList);

        List<Map<String, Object>> result = assessmentMasterService.retriveSubModuleList();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample Submodule", result.get(0).get("submoduleName"));
        verify(assessmentMasterRepository, times(1)).retriveSubModuleList();
    }

    @Test
    public void testRetriveScheduleForList() {
        List<Map<String, Object>> scheduleForList = new ArrayList<>();
        Map<String, Object> scheduleFor = new HashMap<>();
        scheduleFor.put("scheduleForId", 1);
        scheduleFor.put("scheduleForName", "Sample ScheduleFor");
        scheduleForList.add(scheduleFor);

        when(assessmentMasterRepository.retriveScheduleForList()).thenReturn(scheduleForList);

        List<Map<String, Object>> result = assessmentMasterService.retriveScheduleForList();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample ScheduleFor", result.get(0).get("scheduleForName"));
        verify(assessmentMasterRepository, times(1)).retriveScheduleForList();
    }

    @Test
    public void testGetQuestionarByScheduleId_Success() {
        Integer scheduleId = 1;
        AssessmentSetting assessmentSetting = new AssessmentSetting();
        assessmentSetting.setNumberOfQuestion(5);

        when(assessmentSettingRespository.findFirst1ByScheduleForMaster_scheduleForId(scheduleId)).thenReturn(assessmentSetting);

        List<AssessmentMaster> assessmentMastersList = new ArrayList<>();
        assessmentMastersList.add(assessmentMaster);

        when(assessmentMasterRepository.getQuestionarByScheduleId(scheduleId, 5)).thenReturn(assessmentMastersList);

        ResponseEntity<?> result = assessmentMasterService.getQuestionarByScheduleId(scheduleId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(assessmentSettingRespository, times(1)).findFirst1ByScheduleForMaster_scheduleForId(scheduleId);
        verify(assessmentMasterRepository, times(1)).getQuestionarByScheduleId(scheduleId, 5);
    }

    @Test
    public void testGetQuestionarByScheduleId_NotFound() {
        Integer scheduleId = 1;
        AssessmentSetting assessmentSetting = new AssessmentSetting();
        assessmentSetting.setNumberOfQuestion(5);

        when(assessmentSettingRespository.findFirst1ByScheduleForMaster_scheduleForId(scheduleId)).thenReturn(assessmentSetting);

        when(assessmentMasterRepository.getQuestionarByScheduleId(scheduleId, 5)).thenReturn(Collections.emptyList());

        ResponseEntity<?> result = assessmentMasterService.getQuestionarByScheduleId(scheduleId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("No questions found for session ID: " + scheduleId, result.getBody());
        verify(assessmentSettingRespository, times(1)).findFirst1ByScheduleForMaster_scheduleForId(scheduleId);
        verify(assessmentMasterRepository, times(1)).getQuestionarByScheduleId(scheduleId, 5);
    }
}


