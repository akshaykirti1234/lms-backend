package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.SessionMasterDto;
import com.csmtech.entity.*;
import com.csmtech.exceptions.SessionNotFoundException;
import com.csmtech.repository.*;
import com.csmtech.service.SessionMasterServiceImpl;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SessionMasterServiceImplTest {

    @InjectMocks
    private SessionMasterServiceImpl sessionMasterService;

    @Mock
    private SessionMasterRepository sessionRepo;

    @Mock
    private SubModuleRepository subModuleRepo;

    @Mock
    private ScheduleForMasterRepository schRepo;

    @Mock
    private SessionResultStatusRepository sessionResultStatusRepository;

    @Mock
    private Logger logger;

    private SessionMasterDto sessionMasterDto;
    private SessionMaster sessionMaster;
    private SubModule subModule;
    private ScheduleForMaster scheduleForMaster;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        subModule = new SubModule();
        subModule.setSubmoduleId(1);

        scheduleForMaster = new ScheduleForMaster();
        scheduleForMaster.setScheduleForId(1);

        sessionMaster = new SessionMaster();
        sessionMaster.setSessionId(1);
        sessionMaster.setSessionName("Test Session");
        sessionMaster.setSubModule(subModule);
        sessionMaster.setScheduleFor(scheduleForMaster);

        sessionMasterDto = new SessionMasterDto();
        sessionMasterDto.setSessionid(1);
        sessionMasterDto.setSessionName("Test Session");
        sessionMasterDto.setSubModuleId(1);
        sessionMasterDto.setScheduleForId(1);
    }

    @Test
    public void testSaveSessionMaster() {
        when(subModuleRepo.findById(anyInt())).thenReturn(Optional.of(subModule));
        when(schRepo.findById(anyInt())).thenReturn(Optional.of(scheduleForMaster));
        when(sessionRepo.save(any(SessionMaster.class))).thenReturn(sessionMaster);

        SessionMaster result = sessionMasterService.saveSessionMaster(sessionMasterDto);

        assertNotNull(result);
        assertEquals(sessionMaster.getSessionId(), result.getSessionId());
        verify(sessionRepo, times(1)).save(any(SessionMaster.class));
    }

    @Test
    public void testGetSessionMasterById_Success() throws SessionNotFoundException {
        when(sessionRepo.findById(anyInt())).thenReturn(Optional.of(sessionMaster));

        SessionMasterDto result = sessionMasterService.getSessionMasterById(1);

        assertNotNull(result);
        assertEquals(sessionMaster.getSessionId(), result.getSessionid());
        verify(sessionRepo, times(1)).findById(anyInt());
    }

    @Test
    public void testGetSessionMasterById_NotFound() {
        when(sessionRepo.findById(anyInt())).thenReturn(Optional.empty());

        SessionNotFoundException exception = assertThrows(SessionNotFoundException.class, () -> {
            sessionMasterService.getSessionMasterById(1);
        });

        assertEquals("session not found with the id : 1", exception.getMessage());
        verify(sessionRepo, times(1)).findById(anyInt());
    }

    @Test
    public void testDeleteSessionMasterById_Success() throws SessionNotFoundException {
        when(sessionRepo.findById(anyInt())).thenReturn(Optional.of(sessionMaster));
        doNothing().when(sessionRepo).deleteSession(anyInt());

        sessionMasterService.deleteSessionMasterById(1);

        verify(sessionRepo, times(1)).findById(anyInt());
        verify(sessionRepo, times(1)).deleteSession(anyInt());
    }

    @Test
    public void testDeleteSessionMasterById_NotFound() {
        when(sessionRepo.findById(anyInt())).thenReturn(Optional.empty());

        SessionNotFoundException exception = assertThrows(SessionNotFoundException.class, () -> {
            sessionMasterService.deleteSessionMasterById(1);
        });

        assertEquals("session not found with the id : 1", exception.getMessage());
        verify(sessionRepo, times(1)).findById(anyInt());
        verify(sessionRepo, never()).deleteSession(anyInt());
    }

    @Test
    public void testGetAllSessionMaster() {
        List<Map<String, Object>> sessionList = new ArrayList<>();
        Map<String, Object> session = new HashMap<>();
        session.put("sessionId", 1);
        session.put("sessionName", "Test Session");
        sessionList.add(session);

        when(sessionRepo.getAllSessionMaster()).thenReturn(sessionList);

        List<Map<String, Object>> result = sessionMasterService.getAllSessionMaster();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Session", result.get(0).get("sessionName"));
        verify(sessionRepo, times(1)).getAllSessionMaster();
    }

    @Test
    public void testCheckIsLastSession() {
        when(sessionRepo.checkIsLastSession(anyInt())).thenReturn(true);

        Boolean result = sessionMasterService.checkIsLastSession(1);

        assertTrue(result);
        verify(sessionRepo, times(1)).checkIsLastSession(anyInt());
    }

    @Test
    public void testCheckBoxValidation_True() {
        when(sessionRepo.checkBoxValidation(anyInt())).thenReturn("true");

        Boolean result = sessionMasterService.checkBoxValidation(1);

        assertTrue(result);
        verify(sessionRepo, times(1)).checkBoxValidation(anyInt());
    }

    @Test
    public void testCheckBoxValidation_False() {
        when(sessionRepo.checkBoxValidation(anyInt())).thenReturn("false");

        Boolean result = sessionMasterService.checkBoxValidation(1);

        assertFalse(result);
        verify(sessionRepo, times(1)).checkBoxValidation(anyInt());
    }

    @Test
    public void testGetSessionByScheduleId_Found() {
        List<SessionMaster> sessionList = Collections.singletonList(sessionMaster);

        when(sessionRepo.getSessionByScheduleId(anyInt())).thenReturn(sessionList);

        ResponseEntity<?> response = sessionMasterService.getSessionByScheduleId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(sessionRepo, times(1)).getSessionByScheduleId(anyInt());
    }

    @Test
    public void testGetSessionByScheduleId_NotFound() {
        when(sessionRepo.getSessionByScheduleId(anyInt())).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = sessionMasterService.getSessionByScheduleId(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(sessionRepo, times(1)).getSessionByScheduleId(anyInt());
    }

    @Test
    public void testGetSessionByscheduleForIdAndUserId_Found() {
        List<SessionMaster> sessionList = Collections.singletonList(sessionMaster);
        SessionResultStatus resultStatus = new SessionResultStatus();
        resultStatus.setStatusOfResult(true);

        when(sessionRepo.getSessionByScheduleId(anyInt())).thenReturn(sessionList);
        when(sessionResultStatusRepository.findSessionMasterBySessionIdAndUserId(anyInt(), anyInt())).thenReturn(resultStatus);

        ResponseEntity<?> response = sessionMasterService.getSessionByscheduleForIdAndUserId(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(sessionRepo, times(1)).getSessionByScheduleId(anyInt());
        verify(sessionResultStatusRepository, times(1)).findSessionMasterBySessionIdAndUserId(anyInt(), anyInt());
    }

    @Test
    public void testGetSessionByscheduleForIdAndUserId_NotFound() {
        when(sessionRepo.getSessionByScheduleId(anyInt())).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = sessionMasterService.getSessionByscheduleForIdAndUserId(1, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(sessionRepo, times(1)).getSessionByScheduleId(anyInt());
    }
}

