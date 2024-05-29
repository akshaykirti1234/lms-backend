package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.csmtech.dto.SubModuleDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.repository.SubModuleRepository;
import com.csmtech.service.SubModuleServiceImpl;

@SpringBootTest
public class SubModuleServiceImplTest {
	
	@Mock
    private SubModuleRepository subModuleRepository;

    @InjectMocks
    private SubModuleServiceImpl subModuleServiceImpl;

    private SubModuleDto subModuleDto;
    private SubModule subModule;
    private ModuleMaster moduleMaster;

    @BeforeEach
    void setUp() {
        subModuleDto = new SubModuleDto();
        subModuleDto.setSubmoduleId(1);
        subModuleDto.setModuleId(1);
        subModuleDto.setSubmoduleName("Test Submodule");

        moduleMaster = new ModuleMaster();
        moduleMaster.setModuleId(1);

        subModule = new SubModule();
        subModule.setSubmoduleId(1);
        subModule.setModuleMaster(moduleMaster);
        subModule.setSubmoduleName("Test Submodule");
        subModule.setCreatedBy(1);
        subModule.setUpdatedBy(1);
    }

    @Test
    void testGetSubmoduleByModuleId_Found() {
        List<SubModule> submoduleList = Arrays.asList(subModule);

        when(subModuleRepository.getSubmoduleByModuleId(1)).thenReturn(submoduleList);

        ResponseEntity<?> response = subModuleServiceImpl.getSubmoduleByModuleId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(submoduleList, response.getBody());
    }

    @Test
    void testGetSubmoduleByModuleId_NotFound() {
        when(subModuleRepository.getSubmoduleByModuleId(1)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = subModuleServiceImpl.getSubmoduleByModuleId(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(((List<?>) response.getBody()).isEmpty());
    }

    @Test
    void testGetAllSubModules() {
        List<SubModule> submoduleList = Arrays.asList(subModule);

        when(subModuleRepository.getAllSubModules()).thenReturn(submoduleList);

        List<SubModule> result = subModuleServiceImpl.getAllSubModules();

        assertEquals(submoduleList, result);
    }

    @Test
    void testSaveSubModule_Success() {
        when(subModuleRepository.save(any(SubModule.class))).thenReturn(subModule);

        Map<String, Object> response = subModuleServiceImpl.saveSubModule(subModuleDto);

        assertEquals(HttpStatus.CREATED, response.get("status"));
        assertEquals("Sub-Module data saved successfully", response.get("message"));
    }

    @Test
    void testSaveSubModule_Exception() {
        when(subModuleRepository.save(any(SubModule.class))).thenThrow(new RuntimeException("Error"));

        Map<String, Object> response = subModuleServiceImpl.saveSubModule(subModuleDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.get("status"));
        assertEquals("Something went wrong", response.get("message"));
    }

    @Test
    void testGetAllSubModule() {
        List<Map<String, Object>> submoduleList = new ArrayList<>();
        Map<String, Object> submoduleData = new HashMap<>();
        submoduleData.put("submoduleId", 1);
        submoduleData.put("submoduleName", "Test Submodule");
        submoduleList.add(submoduleData);

        when(subModuleRepository.getAllSubModule()).thenReturn(submoduleList);

        List<Map<String, Object>> result = subModuleServiceImpl.getAllSubModule();

        assertEquals(submoduleList, result);
    }

    @Test
    void testDeleteSubModule() {
        doNothing().when(subModuleRepository).deleteSubModule(1);

        subModuleServiceImpl.deleteSubModule(1);

        verify(subModuleRepository, times(1)).deleteSubModule(1);
    }

    @Test
    void testGeteditById() {
        Map<String, Object> submoduleData = new HashMap<>();
        submoduleData.put("submoduleId", 1);
        submoduleData.put("submoduleName", "Test Submodule");

        when(subModuleRepository.updateSubModule(1)).thenReturn(submoduleData);

        Map<String, Object> result = subModuleServiceImpl.geteditById(1);

        assertEquals(submoduleData, result);
    }

}
