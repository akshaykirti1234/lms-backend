package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;

import com.csmtech.dto.SessionAssessmentSettingDto;
import com.csmtech.dto.SessionAssessmentSettingSessionDto;
import com.csmtech.entity.*;
import com.csmtech.repository.SessionAssessmentSettingRepository;
import com.csmtech.service.SessionAssessmentSettingServiceImpl;

public class SessionAssessmentSettingServiceImplTest {

    @InjectMocks
    private SessionAssessmentSettingServiceImpl sessionAssessmentSettingService;

    @Mock
    private SessionAssessmentSettingRepository sessionAssessmentSettingRepository;

    @Mock
    private Logger logger;

    private SessionAssessmentSettingDto sessionAssessmentSettingDto;
    private SessionAssessmentSetting sessionAssessmentSetting;
    private ModuleMaster moduleMaster;
    private SubModule subModule;
    private ScheduleForMaster scheduleForMaster;
    private SessionMaster sessionMaster;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        moduleMaster = new ModuleMaster();
        moduleMaster.setModuleId(1);

        subModule = new SubModule();
        subModule.setSubmoduleId(1);

        scheduleForMaster = new ScheduleForMaster();
        scheduleForMaster.setScheduleForId(1);

        sessionMaster = new SessionMaster();
        sessionMaster.setSessionId(3);

        sessionAssessmentSetting = new SessionAssessmentSetting();
        sessionAssessmentSetting.setModuleMaster(moduleMaster);
        sessionAssessmentSetting.setSubModule(subModule);
        sessionAssessmentSetting.setScheduleForMaster(scheduleForMaster);
        sessionAssessmentSetting.setSessionMaster(sessionMaster);
        sessionAssessmentSetting.setNumberOfQuestion(10);
        sessionAssessmentSetting.setPassingPercentage(60.0);

        sessionAssessmentSettingDto = new SessionAssessmentSettingDto();
        sessionAssessmentSettingDto.setModule(1);
        sessionAssessmentSettingDto.setSubModule(1);
        sessionAssessmentSettingDto.setSchedule(1);
        List<SessionAssessmentSettingSessionDto> sessionWiseList = new ArrayList<>();
        sessionWiseList.add(new SessionAssessmentSettingSessionDto(1, 10, 60.0));
        sessionAssessmentSettingDto.setSessionWiseList(sessionWiseList);
    }

    @Test
    public void testSaveSessionAssessmentSetting() {
        when(sessionAssessmentSettingRepository.save(any(SessionAssessmentSetting.class)))
                .thenReturn(sessionAssessmentSetting);

        SessionAssessmentSetting result = sessionAssessmentSettingService
                .saveSessionAssessmentSetting(sessionAssessmentSettingDto);

        assertNotNull(result);
        assertEquals(sessionAssessmentSetting.getModuleMaster().getModuleId(),
                result.getModuleMaster().getModuleId());
        assertEquals(sessionAssessmentSetting.getSubModule().getSubmoduleId(), result.getSubModule().getSubmoduleId());
        assertEquals(sessionAssessmentSetting.getScheduleForMaster().getScheduleForId(),
                result.getScheduleForMaster().getScheduleForId());
        assertEquals(sessionAssessmentSetting.getSessionMaster().getSessionId(),
                result.getSessionMaster().getSessionId());
        assertEquals(sessionAssessmentSetting.getNumberOfQuestion(), result.getNumberOfQuestion());
        assertEquals(sessionAssessmentSetting.getPassingPercentage(), result.getPassingPercentage());

        verify(sessionAssessmentSettingRepository, times(1)).save(any(SessionAssessmentSetting.class));
    }

    @Test
    public void testGetSessionAssessmentSetting() {
        List<Map<String, Object>> sessionList = new ArrayList<>();
        Map<String, Object> session = new HashMap<>();
        session.put("sessionId", 1);
        session.put("numberOfQuestion", 10);
        session.put("passingPercentage", 60.0);
        sessionList.add(session);

        when(sessionAssessmentSettingRepository.getSessionAssessmentSetting()).thenReturn(sessionList);

        List<Map<String, Object>> result = sessionAssessmentSettingService.getSessionAssessmentSetting();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).get("numberOfQuestion"));
        verify(sessionAssessmentSettingRepository, times(1)).getSessionAssessmentSetting();
    }

    @Test
    public void testGetSessionAssessmentSettingById() {
        Map<String, Object> session = new HashMap<>();
        session.put("sessionId", 1);
        session.put("numberOfQuestion", 10);
        session.put("passingPercentage", 60.0);

        when(sessionAssessmentSettingRepository.getSessionAssessmentSettingById(anyInt())).thenReturn(session);

        Map<String, Object> result = sessionAssessmentSettingService.getSessionAssessmentSettingById(1);

        assertNotNull(result);
        assertEquals(10, result.get("numberOfQuestion"));
        verify(sessionAssessmentSettingRepository, times(1)).getSessionAssessmentSettingById(anyInt());
    }

    @Test
    public void testDeleteSessionAssessmentSetting() {
        doNothing().when(sessionAssessmentSettingRepository).deleteSessionAssessmentSetting(anyInt());

        sessionAssessmentSettingService.deleteSessionAssessmentSetting(1);

        verify(sessionAssessmentSettingRepository, times(1)).deleteSessionAssessmentSetting(anyInt());
    }

    @Test
    public void testUpdateSessionAssessmentSetting() {
        doNothing().when(sessionAssessmentSettingRepository).updateSessionAssessmentSetting(anyInt(), anyInt(),
                anyDouble());

        sessionAssessmentSettingService.updateSessionAssessmentSetting(1, 15, 70.0);

        verify(sessionAssessmentSettingRepository, times(1)).updateSessionAssessmentSetting(anyInt(), anyInt(),
                anyDouble());
    }

    @Test
    public void testGetSessionforAssessmentSetting() {
        List<Map<String, Object>> sessionList = new ArrayList<>();
        Map<String, Object> session = new HashMap<>();
        session.put("sessionId", 1);
        session.put("sessionName", "Test Session");
        sessionList.add(session);

        when(sessionAssessmentSettingRepository.getSessionforAssessmentSetting(anyInt())).thenReturn(sessionList);

        List<Map<String, Object>> result = sessionAssessmentSettingService.getSessionforAssessmentSetting(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Session", result.get(0).get("sessionName"));
        verify(sessionAssessmentSettingRepository, times(1)).getSessionforAssessmentSetting(anyInt());
    }
}

