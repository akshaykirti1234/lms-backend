package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.csmtech.dto.ScheduleForMasterDto;
import com.csmtech.entity.Author;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.entity.Technology;
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
        scheduleForMasterDto.setSubModule(new SubModule());
        scheduleForMasterDto.setAuthor(new Author());
        Technology tech = new Technology();
        tech.setTechId(1);
        scheduleForMasterDto.setTechnology(tech);
        scheduleForMasterDto.setNoOfSession(5);
        scheduleForMasterDto.setNoOfHours(10);

        scheduleForMaster = new ScheduleForMaster();
        scheduleForMaster.setScheduleForId(1);
        scheduleForMaster.setScheduleForName("Test Schedule");
        scheduleForMaster.setSubModule(new SubModule());
        scheduleForMaster.setAuthor(new Author());
        scheduleForMaster.setTechnology(tech);
        scheduleForMaster.setNoOfSession(5);
        scheduleForMaster.setNoOfHours(10);
    }

    @Test
    void testSaveScheduleForm() {
        when(scheduleForMasterRepository.save(any(ScheduleForMaster.class))).thenReturn(scheduleForMaster);

        ResponseEntity<?> response = scheduleForMasterService.saveScheduleForm(scheduleForMasterDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(scheduleForMasterRepository, times(1)).save(any(ScheduleForMaster.class));
    }

    @SuppressWarnings("deprecation")
	@Test
    void testUpdateScheduleForm() throws IsLastSessionException {
        when(scheduleForMasterRepository.getById(anyInt())).thenReturn(scheduleForMaster);
        when(scheduleForMasterRepository.save(any(ScheduleForMaster.class))).thenReturn(scheduleForMaster);

        ResponseEntity<?> response = scheduleForMasterService.updateScheduleForm(scheduleForMasterDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(scheduleForMasterRepository, times(1)).getById(anyInt());
        verify(scheduleForMasterRepository, times(1)).save(any(ScheduleForMaster.class));
    }

    @SuppressWarnings("deprecation")
	@Test
    void testUpdateScheduleForm_WithException() {
        scheduleForMasterDto.setNoOfSession(3);

        when(scheduleForMasterRepository.getById(anyInt())).thenReturn(scheduleForMaster);

        IsLastSessionException exception = assertThrows(IsLastSessionException.class, () -> {
            scheduleForMasterService.updateScheduleForm(scheduleForMasterDto);
        });

        assertEquals("You can not enter number which is less than previous number.", exception.getMessage());
        verify(scheduleForMasterRepository, times(1)).getById(anyInt());
    }

    @Test
    void testGetAllScheduleForm() {
        List<ScheduleForMaster> scheduleList = new ArrayList<>();
        scheduleList.add(scheduleForMaster);

        when(scheduleForMasterRepository.getAllScheduleForm()).thenReturn(scheduleList);

        List<ScheduleForMaster> result = scheduleForMasterService.getAllScheduleForm();

        assertEquals(1, result.size());
        verify(scheduleForMasterRepository, times(1)).getAllScheduleForm();
    }

    @Test
    void testUpdateScheduleFor() {
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
    void testDeleteScheduleFor() {
        doNothing().when(scheduleForMasterRepository).deleteScheduleFor(anyInt());

        ResponseEntity<?> response = scheduleForMasterService.deleteScheduleFor(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(scheduleForMasterRepository, times(1)).deleteScheduleFor(anyInt());
    }

    @Test
    void testGetScheduleForBySubModuleId() {
        List<Map<String, Object>> scheduleList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("scheduleForId", 1);
        scheduleList.add(map);

        when(scheduleForMasterRepository.findBysubModuleId(anyInt())).thenReturn(scheduleList);

        List<Map<String, Object>> result = scheduleForMasterService.getScheduleForBySubModuleId(1);

        assertEquals(1, result.size());
        verify(scheduleForMasterRepository, times(1)).findBysubModuleId(anyInt());
    }

    @Test
    void testGetScheduleBySubModuleId() {
        List<ScheduleForMaster> scheduleList = new ArrayList<>();
        scheduleList.add(scheduleForMaster);

        when(scheduleForMasterRepository.getScheduleBySubModuleId(anyInt())).thenReturn(scheduleList);

        ResponseEntity<List<ScheduleForMaster>> response = scheduleForMasterService.getScheduleBySubModuleId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(scheduleList, response.getBody());
        verify(scheduleForMasterRepository, times(1)).getScheduleBySubModuleId(anyInt());
    }

    @Test
    void testGetScheduleBySubModuleId_NotFound() {
        when(scheduleForMasterRepository.getScheduleBySubModuleId(anyInt())).thenReturn(new ArrayList<>());

        ResponseEntity<List<ScheduleForMaster>> response = scheduleForMasterService.getScheduleBySubModuleId(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(scheduleForMasterRepository, times(1)).getScheduleBySubModuleId(anyInt());
    }
}
