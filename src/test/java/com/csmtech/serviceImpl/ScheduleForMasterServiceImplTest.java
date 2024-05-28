package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.ScheduleForMasterDto;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.exceptions.IsLastSessionException;
import com.csmtech.repository.ScheduleForMasterRepository;
import com.csmtech.repository.SessionMasterRepository;
import com.csmtech.service.ScheduleForMasterServiceImpl;

@SpringBootTest
public class ScheduleForMasterServiceImplTest {

	@Mock
    private ScheduleForMasterRepository scheduleForMasterRepository;

    @Mock
    private SessionMasterRepository sessionMasterRepository;

    @InjectMocks
    private ScheduleForMasterServiceImpl scheduleForMasterService;

    private ScheduleForMasterDto scheduleForMasterDto;
    private ScheduleForMaster scheduleForMaster;

    @BeforeEach
    void setUp() {
        scheduleForMasterDto = new ScheduleForMasterDto();
        scheduleForMasterDto.setScheduleForId(1);
        scheduleForMasterDto.setScheduleForName("Test Schedule");
        // Set other necessary fields for scheduleForMasterDto

        scheduleForMaster = new ScheduleForMaster();
        scheduleForMaster.setScheduleForId(1);
        scheduleForMaster.setScheduleForName("Test Schedule");
        // Set other necessary fields for scheduleForMaster
    }

    @Test
    void testSaveScheduleForm_Success() {
        when(scheduleForMasterRepository.save(any(ScheduleForMaster.class))).thenReturn(scheduleForMaster);

        ResponseEntity<?> response = scheduleForMasterService.saveScheduleForm(scheduleForMasterDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(scheduleForMasterRepository, times(1)).save(any(ScheduleForMaster.class));
    }

    @Test
    void testSaveScheduleForm_Failure() {
        when(scheduleForMasterRepository.save(any(ScheduleForMaster.class))).thenReturn(new ScheduleForMaster());

        ResponseEntity<?> response = scheduleForMasterService.saveScheduleForm(scheduleForMasterDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(scheduleForMasterRepository, times(1)).save(any(ScheduleForMaster.class));
    }

    @Test
    void testUpdateScheduleForm_Success() throws IsLastSessionException {
        when(scheduleForMasterRepository.getById(anyInt())).thenReturn(scheduleForMaster);
        when(scheduleForMasterRepository.save(any(ScheduleForMaster.class))).thenReturn(scheduleForMaster);

        ResponseEntity<?> response = scheduleForMasterService.updateScheduleForm(scheduleForMasterDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(scheduleForMasterRepository, times(1)).save(any(ScheduleForMaster.class));
    }

    @Test
    void testUpdateScheduleForm_Failure_LessSessions() {
        scheduleForMasterDto.setNoOfSession(1); // less than existing sessions

        when(scheduleForMasterRepository.getById(anyInt())).thenReturn(scheduleForMaster);

        assertThrows(IsLastSessionException.class, () -> {
            scheduleForMasterService.updateScheduleForm(scheduleForMasterDto);
        });

        verify(scheduleForMasterRepository, never()).save(any(ScheduleForMaster.class));
    }

    @Test
    void testGetAllScheduleForm() {
        when(scheduleForMasterRepository.getAllScheduleForm()).thenReturn(Arrays.asList(scheduleForMaster));

        List<ScheduleForMaster> result = scheduleForMasterService.getAllScheduleForm();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(scheduleForMasterRepository, times(1)).getAllScheduleForm();
    }

    @Test
    void testUpdateScheduleFor_Success() {
        when(scheduleForMasterRepository.findById(anyInt())).thenReturn(Optional.of(scheduleForMaster));

        ResponseEntity<?> response = scheduleForMasterService.updateScheduleFor(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(scheduleForMaster, response.getBody());
        verify(scheduleForMasterRepository, times(1)).findById(anyInt());
    }

    @Test
    void testUpdateScheduleFor_NotFound() {
        when(scheduleForMasterRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<?> response = scheduleForMasterService.updateScheduleFor(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(scheduleForMasterRepository, times(1)).findById(anyInt());
    }

    @Test
    void testDeleteScheduleFor_Success() {
        ResponseEntity<?> response = scheduleForMasterService.deleteScheduleFor(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(scheduleForMasterRepository, times(1)).deleteScheduleFor(anyInt());
    }

    @Test
    void testDeleteScheduleFor_BadRequest() {
        ResponseEntity<?> response = scheduleForMasterService.deleteScheduleFor(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(scheduleForMasterRepository, never()).deleteScheduleFor(anyInt());
    }
}
