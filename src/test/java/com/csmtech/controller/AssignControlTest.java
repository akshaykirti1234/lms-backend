package com.csmtech.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.service.ScheduleForMasterService;
import com.csmtech.service.SubModuleService;

public class AssignControlTest {

    @Mock
    private SubModuleService subModuleService;

    @Mock
    private ScheduleForMasterService scheduleForMasterService;

    @InjectMocks
    private AssignControl assignControl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllSubModules() {
        // Arrange
        List<SubModule> mockSubModulesList = new ArrayList<>();
        SubModule subModule1 = new SubModule();
        subModule1.setSubmoduleId(1);
        subModule1.setSubmoduleName("Module 1");
        mockSubModulesList.add(subModule1);

        SubModule subModule2 = new SubModule();
        subModule2.setSubmoduleId(2);
        subModule2.setSubmoduleName("Module 2");
        mockSubModulesList.add(subModule2);

        when(subModuleService.getAllSubModules()).thenReturn(mockSubModulesList);

        // Act
        ResponseEntity<?> response = assignControl.getAllSubModules();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockSubModulesList, response.getBody());
    }

    @Test
    public void testGetAllScheduleForm() {
        // Arrange
        List<ScheduleForMaster> mockScheduleForMasters = new ArrayList<>();
        ScheduleForMaster schedule1 = new ScheduleForMaster();
        schedule1.setScheduleForId(1);
        schedule1.setScheduleForName("Schedule 1");
        mockScheduleForMasters.add(schedule1);

        ScheduleForMaster schedule2 = new ScheduleForMaster();
        schedule2.setScheduleForId(2);
        schedule2.setScheduleForName("Schedule 2");
        mockScheduleForMasters.add(schedule2);

        when(scheduleForMasterService.getAllScheduleForm()).thenReturn(mockScheduleForMasters);

        // Act
        ResponseEntity<?> response = assignControl.getAllScheduleForm();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockScheduleForMasters, response.getBody());
    }
}
