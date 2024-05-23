package com.csmtech.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.csmtech.entity.Author;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.SubModule;
import com.csmtech.entity.Technology;
import com.csmtech.repository.AuthorRepository;
import com.csmtech.repository.ModuleMasterRepository;
import com.csmtech.repository.ScheduleForMasterRepository;
import com.csmtech.repository.SubModuleRepository;
import com.csmtech.repository.TechnologyRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ScheduleForMasterRepositoryTest {

	 @Autowired
	    private ScheduleForMasterRepository scheduleForMasterRepository;

	    // Add repositories for related entities
	    @Autowired
	    private ModuleMasterRepository moduleMasterRepository;

	    @Autowired
	    private SubModuleRepository subModuleRepository;

	    @Autowired
	    private AuthorRepository authorRepository;

	    @Autowired
	    private TechnologyRepository technologyRepository;

	    private ScheduleForMaster scheduleFor;
	    private SubModule subModule;
	    private Author author;
	    private Technology technology;

	    @BeforeEach
	    public void setUp() {
	        // Initialize and save ModuleMaster
	        ModuleMaster moduleMaster = new ModuleMaster();
	        moduleMaster.setModuleName("Test Module");
	        moduleMaster.setModuleDescription("Test Module Description");
	        moduleMaster.setLogo("Test Logo");
	        moduleMaster.setCreatedBy(1);
	        moduleMaster.setUpdatedBy(1);
	        moduleMaster = moduleMasterRepository.save(moduleMaster);

	        // Initialize and save SubModule
	        subModule = new SubModule();
	        subModule.setSubmoduleName("Test SubModule");
	        subModule.setModuleMaster(moduleMaster);
	        subModule.setCreatedBy(1);
	        subModule.setUpdatedBy(1);
	        subModule = subModuleRepository.save(subModule);

	        // Initialize and save Author
	        author = new Author();
	        author.setAuthName("Test Author");
	        author = authorRepository.save(author);

	        // Initialize and save Technology
	        technology = new Technology();
	        technology.setTechName("Test Technology");
	        technology = technologyRepository.save(technology);

	        // Initialize and save ScheduleForMaster
	        scheduleFor = new ScheduleForMaster();
	        scheduleFor.setScheduleForName("Test Schedule");
	        scheduleFor.setNoOfSession(10);
	        scheduleFor.setNoOfHours(20);
	        scheduleFor.setIsAssessmentPrepared(true);
	        scheduleFor.setCreatedBy(1);
	        scheduleFor.setUpdatedBy(1);
	        scheduleFor.setSubModule(subModule); // Set the subModule
	        scheduleFor.setAuthor(author); // Set the author
	        scheduleFor.setTechnology(technology); // Set the technology
	        scheduleFor = scheduleForMasterRepository.save(scheduleFor); // Save scheduleFor
	    }

	    @Test
	    public void testGetAllScheduleForm() {
	        List<ScheduleForMaster> schedules = scheduleForMasterRepository.getAllScheduleForm();
	        assertThat(schedules).isNotNull().isNotEmpty();
	    }

	    @Test
	    @Transactional
	    @Rollback
	    public void testDeleteScheduleFor() {
	        // Check that the schedule exists before deletion
	        assertThat(scheduleForMasterRepository.findById(scheduleFor.getScheduleForId())).isPresent();

	        // Perform delete operation
	        scheduleForMasterRepository.deleteScheduleFor(scheduleFor.getScheduleForId());

	        // Check that the schedule has been marked as deleted
	        List<ScheduleForMaster> schedules = scheduleForMasterRepository.getAllScheduleForm();
	        boolean isDeleted = schedules.stream().noneMatch(s -> s.getScheduleForId().equals(scheduleFor.getScheduleForId()));
	        assertThat(isDeleted).isTrue();
	    }

	    @Test
	    @Transactional
	    @Rollback
	    public void testUpdateFlagByScheduleId() {
	        // Update the flag
	        Integer result = scheduleForMasterRepository.updateFlagByScheduleId(scheduleFor.getScheduleForId());
	        assertThat(result).isEqualTo(1);

	        // Verify the update
	        ScheduleForMaster updatedSchedule = scheduleForMasterRepository.findById(scheduleFor.getScheduleForId()).orElse(null);
	        assertThat(updatedSchedule).isNotNull();
	        assertThat(updatedSchedule.getIsAssessmentPrepared()).isTrue();
	    }
	    
	    
	    

	    @Test
	    public void testFindBysubModuleId() {
	        List<Map<String, Object>> result = scheduleForMasterRepository.findBysubModuleId(scheduleFor.getSubModule().getSubmoduleId());
	        assertThat(result).isNotNull().isNotEmpty();
	    }

	    @Test
	    public void testGetScheduleBySubModuleId() {
	        List<ScheduleForMaster> schedules = scheduleForMasterRepository.getScheduleBySubModuleId(scheduleFor.getSubModule().getSubmoduleId());
	        assertThat(schedules).isNotNull().isNotEmpty();
	    }
    
    
    
}
