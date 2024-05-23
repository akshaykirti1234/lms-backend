package com.csmtech.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import java.util.List;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.repository.ModuleMasterRepository;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ModuleMasterRepositoryTest {

    @Autowired
    private ModuleMasterRepository moduleMasterRepository;

    private ModuleMaster module;

    @BeforeEach
    public void setUp() {
        module = new ModuleMaster();
        module.setModuleName("Test Module");
        module.setModuleDescription("Description");
        module.setLogo("logo.png");
        module.setCreatedBy(1);
        module.setUpdatedBy(1);
        module = moduleMasterRepository.save(module);
    }

    @Test
    public void testFindAllModule() {
        List<ModuleMaster> modules = moduleMasterRepository.findAllModule();
        assertThat(modules).isNotNull().isNotEmpty();
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteModuleById() {
        assertThat(moduleMasterRepository.findById(module.getModuleId())).isPresent();

        moduleMasterRepository.deleteModuleById(module.getModuleId());

        List<ModuleMaster> modules = moduleMasterRepository.findAllModule();
        boolean isDeleted = modules.stream().noneMatch(m -> m.getModuleId().equals(module.getModuleId()));
        assertThat(isDeleted).isTrue();
    }
}
