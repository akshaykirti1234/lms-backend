package com.csmtech.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.csmtech.entity.ModuleMaster;

public interface ModuleMasterRepository extends JpaRepository<ModuleMaster, Integer> {
	@Query(value = "select * from modulemaster where DELETEDFLAG=0", nativeQuery = true)
	List<ModuleMaster> findAllModule();

	@Modifying
	@Transactional
	@Query(value = "update modulemaster set DELETEDFLAG=1 where MODULEID=:moduleId", nativeQuery = true)
	void deleteModuleById(Integer moduleId);
}
