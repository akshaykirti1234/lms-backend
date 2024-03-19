package com.csmtech.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.csmtech.entity.SubModule;

public interface SubModuleRepository extends JpaRepository<SubModule, Integer> {

	@Query("from SubModule  where moduleMaster.moduleId = :moduleId")
	List<SubModule> getSubmoduleById(Integer moduleId);

	@Query(value = "SELECT sub.SUBMODULEID, sub.SUBMODULENAME, m.MODULEID, m.MODULENAME " + "FROM submodulemaster sub "
			+ "INNER JOIN modulemaster m ON m.MODULEID = sub.MODULEID "
			+ "WHERE sub.DELETEDFLAG = 0", nativeQuery = true)
	List<Map<String, Object>> getAllSubModule();

	@Query("from SubModule where deletedFlag = false")
	List<SubModule> getAllSubModules();

	@Transactional
	@Modifying
	@Query(value = "update submodulemaster set DELETEDFLAG=1 where SUBMODULEID=:id", nativeQuery = true)
	void deleteSubModule(Integer id);

	@Query(value = "SELECT sub.SUBMODULEID, sub.SUBMODULENAME, m.MODULEID, m.MODULENAME " + "FROM submodulemaster sub "
			+ "INNER JOIN modulemaster m ON m.MODULEID = sub.MODULEID "
			+ "WHERE sub.DELETEDFLAG = 0 AND sub.SUBMODULEID = :id", nativeQuery = true)
	Map<String, Object> updateSubModule(Integer id);

}
