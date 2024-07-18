package com.csmtech.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.csmtech.entity.ScheduleForMaster;

public interface ScheduleForMasterRepository extends JpaRepository<ScheduleForMaster, Integer> {

	@Query("from ScheduleForMaster where deletedFlag = false")
	List<ScheduleForMaster> getAllScheduleForm();

	@Modifying
	@Transactional
	@Query("update ScheduleForMaster set deletedFlag = true where scheduleForId = :scheduleForId")
	void deleteScheduleFor(Integer scheduleForId);

	@Modifying
	@Transactional
	@Query("UPDATE ScheduleForMaster  SET isAssessmentPrepared = true WHERE scheduleForId = :scheduleForId")
	Integer updateFlagByScheduleId(@Param("scheduleForId") Integer scheduleForId);

//	@Query(value = "SELECT SF.SCHEDULEFORID, SF.SCHEDULEFOR, SF.NOOFSESSION,AU.AUTHNAME\r\n"
//			+ "FROM SCHEDULEFORMASTER SF\r\n" + "INNER JOIN SUBMODULEMASTER SM\r\n"
//			+ "ON(SF.SUBMODULEID=SM.SUBMODULEID)\r\n" + "INNER JOIN AUTHOR AU\r\n" + "ON(SF.AUTHID=AU.AUTHID)\r\n"
//			+ "WHERE SF.SUBMODULEID = :id AND SF.DELETEDFLAG = 0;", nativeQuery = true)
	@Query(value = "SELECT SF.SCHEDULEFORID, SF.SCHEDULEFOR, SF.NOOFSESSION, AU.AUTHNAME "
			+ "FROM scheduleformaster SF " + "INNER JOIN submodulemaster SM ON (SF.SUBMODULEID = SM.SUBMODULEID) "
			+ "INNER JOIN author AU ON (SF.AUTHID = AU.AUTHID) "
			+ "WHERE SF.SUBMODULEID = :id AND SF.DELETEDFLAG = 0;", nativeQuery = true)
	List<Map<String, Object>> findBysubModuleId(Integer id);

	@Query("from ScheduleForMaster where subModule.submoduleId = :submoduleId and deletedFlag = false ")
	List<ScheduleForMaster> getScheduleBySubModuleId(Integer submoduleId);

}
