package com.csmtech.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.dto.TechnologyDto;
import com.csmtech.entity.Technology;
import com.csmtech.repository.TechnologyRepository;
import com.csmtech.service.TechnologyServiceImpl;

@SpringBootTest
public class TechnologyServiceImplTest {

	@InjectMocks
    private TechnologyServiceImpl technologyService;

    @Mock
    private TechnologyRepository technologyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTechnologies() {
        List<Technology> technologies = new ArrayList<>();
        Technology techObj=new Technology();
        techObj.setTechId(1);
        techObj.setTechName("Java");
        techObj.setCreatedBy(1);
        techObj.setUpdatedBy(1);
        Technology techObj1=new Technology();
        techObj1.setTechId(2);
        techObj1.setTechName("Python");
        techObj1.setCreatedBy(1);
        techObj1.setUpdatedBy(1);
        technologies.add(techObj);
        technologies.add(techObj1);
        when(technologyRepository.getAllTechnologies()).thenReturn(technologies);

        List<Technology> result = technologyService.getAllTechnologies();

        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getTechName());
        verify(technologyRepository, times(1)).getAllTechnologies();
    }

    @Test
    void testSaveTechnology() {
        TechnologyDto technologyDto = new TechnologyDto();
        technologyDto.setTechId(1);
        technologyDto.setTechName("Java");

        Technology technology = new Technology();
        technology.setTechId(1);
        technology.setTechName("Java");
        technology.setCreatedBy(1);
        technology.setUpdatedBy(1);

        when(technologyRepository.save(any(Technology.class))).thenReturn(technology);

        Technology result = technologyService.saveTechnology(technologyDto);

        assertNotNull(result);
        assertEquals("Java", result.getTechName());
        verify(technologyRepository, times(1)).save(any(Technology.class));
    }

    @Test
    void testDeleteTechnology() {
        Integer techId = 1;
        doNothing().when(technologyRepository).deleteTechnology(techId);

        technologyService.deleteTechnology(techId);

        verify(technologyRepository, times(1)).deleteTechnology(techId);
    }

    @Test
    void testEditTechnology() {
        Integer techId = 1;
        Technology technology = new Technology();
        technology.setTechId(techId);
        technology.setTechName("Java");

        when(technologyRepository.findById(techId)).thenReturn(Optional.of(technology));

        Technology result = technologyService.editTechnology(techId);

        assertNotNull(result);
        assertEquals("Java", result.getTechName());
        verify(technologyRepository, times(1)).findById(techId);
    }
}
