package com.csmtech.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.SubModuleDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.service.ModuleMasterService;
import com.csmtech.service.SubModuleService;

public class SubModuleControllerTest {

    @Mock
    private ModuleMasterService moduleMasterService;

    @Mock
    private SubModuleService subModuleService;

    @InjectMocks
    private SubModuleController subModuleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testGetModuleList() {
        // Arrange
        List<ModuleMaster> moduleList = new ArrayList<>();
        when(moduleMasterService.getModuleMaster()).thenReturn(moduleList);

        // Act
        ResponseEntity<?> responseEntity = subModuleController.getModuleList();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(moduleList, responseEntity.getBody());
    }

    @Test
    public void testSaveSubModuleMaster() {
        // Arrange
        SubModuleDto subModuleDto = new SubModuleDto();
        Map<String, Object> result = new HashMap<>();
        result.put("message", "SubModule Saved Successfully");
        when(subModuleService.saveSubModule(any(SubModuleDto.class))).thenReturn(result);

        // Act
        ResponseEntity<?> responseEntity = subModuleController.saveSubModuleMaster(subModuleDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(result, responseEntity.getBody());
    }

    @Test
    public void testGetAllSubModule() {
        // Arrange
        List<Map<String, Object>> subModuleList = new ArrayList<>();
        when(subModuleService.getAllSubModule()).thenReturn(subModuleList);

        // Act
        ResponseEntity<?> responseEntity = subModuleController.getAllSubModule();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(subModuleList, responseEntity.getBody());
    }

    @Test
    public void testDeleteSubModule() {
        // Arrange
        Integer id = 1;
        Map<String, Object> response = new HashMap<>();
        response.put("status", "deleted");
        //when(subModuleService.deleteSubModule(id)).thenReturn(response);

        // Act
        ResponseEntity<?> responseEntity = subModuleController.deleteSubModule(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    public void testGetEditById() {
        // Arrange
        Integer id = 1;
        Map<String, Object> update = new HashMap<>();
        when(subModuleService.geteditById(id)).thenReturn(update);

        // Act
        ResponseEntity<?> responseEntity = subModuleController.geteditById(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(update, responseEntity.getBody());
    }

    @Test
    public void testGetSubModuleByModuleId() {
        // Arrange
      MockitoAnnotations.initMocks(this); // Initialize mocks
        Integer moduleId = 1;

        // Set up response entity
        ResponseEntity<?> expectedResponseEntity =new ResponseEntity<>("", HttpStatus.OK);
        //when(subModuleService.getSubmoduleByModuleId(moduleId)).thenReturn(expectedResponseEntity);
       doReturn(expectedResponseEntity).when(subModuleService).getSubmoduleByModuleId(moduleId);
        // Act
        ResponseEntity<?> responseEntity = subModuleController.getSubModuleByModuleId(moduleId);

        // Assert
        assertEquals(expectedResponseEntity,responseEntity); // Check status code
    }
}

