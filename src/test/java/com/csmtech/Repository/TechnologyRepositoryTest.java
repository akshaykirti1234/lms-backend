package com.csmtech.Repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.transaction.annotation.Transactional;

import com.csmtech.entity.Technology;
import com.csmtech.repository.TechnologyRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TechnologyRepositoryTest {

    @Autowired
    private TechnologyRepository technologyRepository;

    @BeforeEach
    public void setUp() {
        // Add initial data to the in-memory database
        Technology tech1 = new Technology();
        tech1.setTechName("Java");
        tech1.setCreatedBy(1);
        tech1.setUpdatedBy(1);

        Technology tech2 = new Technology();
        tech2.setTechName("Python");
        tech2.setCreatedBy(1);
        tech2.setUpdatedBy(1);

        Technology tech3 = new Technology();
        tech3.setTechName("JavaScript");
        tech3.setCreatedBy(1);
        tech3.setUpdatedBy(1);

        technologyRepository.save(tech1);
        technologyRepository.save(tech2);
        technologyRepository.save(tech3);
    }

    @Test
    public void testGetAllTechnologies() {
        List<Technology> technologies = technologyRepository.getAllTechnologies();
        assertThat(technologies).isNotNull().hasSize(9);
    }

    @Test
    @Transactional
    public void testDeleteTechnology() {
        technologyRepository.deleteTechnology(1);
        List<Technology> technologies = technologyRepository.findAll();
        Technology deletedTechnology = technologies.stream().filter(t -> t.getTechId().equals(1)).findFirst().orElse(null);
        assertThat(deletedTechnology).isNotNull();
    }

    @Test
    public void testFindByTechnologyId() {
        // Test for existing and non-deleted technology
        Technology technology = technologyRepository.findById(1).orElse(null);
        assertThat(technology).isNotNull();
        assertThat(technology.getTechName()).isEqualTo("JAVA");

        // Test for non-existing technology
        Technology nonExistingTechnology = technologyRepository.findById(999).orElse(null);
        assertThat(nonExistingTechnology).isNull();
    }
}
