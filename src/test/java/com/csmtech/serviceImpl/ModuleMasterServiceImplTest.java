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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.dto.ModuleMasterDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.repository.ModuleMasterRepository;
import com.csmtech.service.ModuleMasterServiceImpl;

import org.slf4j.Logger;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ModuleMasterServiceImplTest {

    @InjectMocks
    private ModuleMasterServiceImpl moduleMasterService;

    @Mock
    private ModuleMasterRepository moduleMasterRepository;

    @Mock
    private Logger logger;

    private ModuleMasterDto moduleMasterDto;
    private ModuleMaster moduleMaster;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        moduleMasterDto = new ModuleMasterDto();
        moduleMasterDto.setModuleId(1);
        moduleMasterDto.setModuleName("Test Module");
        moduleMasterDto.setModuleDescription("Test Description");
        moduleMasterDto.setLogo("Test Logo");

        moduleMaster = new ModuleMaster();
        moduleMaster.setModuleId(1);
        moduleMaster.setModuleName("Test Module");
        moduleMaster.setModuleDescription("Test Description");
        moduleMaster.setLogo("Test Logo");
        moduleMaster.setCreatedBy(1);
    }

    @Test
    public void testGetModuleMaster_Success() {
        List<ModuleMaster> moduleList = new ArrayList<>();
        moduleList.add(moduleMaster);

        when(moduleMasterRepository.findAllModule()).thenReturn(moduleList);

        List<ModuleMaster> result = moduleMasterService.getModuleMaster();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Module", result.get(0).getModuleName());
        verify(moduleMasterRepository, times(1)).findAllModule();
    }

    @Test
    public void testSaveModule_Success() {
        when(moduleMasterRepository.save(any(ModuleMaster.class))).thenReturn(moduleMaster);

        ModuleMaster result = moduleMasterService.saveModule(moduleMasterDto);
        assertNotNull(result);
        assertEquals("Test Module", result.getModuleName());
        verify(moduleMasterRepository, times(1)).save(any(ModuleMaster.class));
    }

    @Test
    public void testSaveModule_Failure() {
        doThrow(new RuntimeException("Database Error")).when(moduleMasterRepository).save(any(ModuleMaster.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            moduleMasterService.saveModule(moduleMasterDto);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(moduleMasterRepository, times(1)).save(any(ModuleMaster.class));
    }

    @Test
    public void testGetModuleById_Success() {
        when(moduleMasterRepository.findById(anyInt())).thenReturn(Optional.of(moduleMaster));

        ModuleMaster result = moduleMasterService.getModuleById(1);
        assertNotNull(result);
        assertEquals(1, result.getModuleId());
        assertEquals("Test Module", result.getModuleName());
        verify(moduleMasterRepository, times(1)).findById(1);
    }

    @Test
    public void testGetModuleById_NotFound() {
        when(moduleMasterRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            moduleMasterService.getModuleById(1);
        });

        assertEquals("No value present", exception.getMessage());
        verify(moduleMasterRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteModuleById_Success() {
        doNothing().when(moduleMasterRepository).deleteModuleById(anyInt());

        moduleMasterService.deleteModuleById(1);
        verify(moduleMasterRepository, times(1)).deleteModuleById(1);
    }
}
