package com.csmtech.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.AssessmentMaster;

@Repository
public interface AssessmentMasterRepository extends JpaRepository<AssessmentMaster, Integer> {

	@Query(value = "SELECT a.ASSESSMENTMASTERID , mo.MODULEID, mo.MODULENAME, sb.SUBMODULEID ,sb.SUBMODULENAME ,sf.SCHEDULEFOR,sf.SCHEDULEFORID, a.QUESTION, a.OPTION1, a.OPTION2,a.OPTION3,a.OPTION4,a.ANSWER\r\n"
			+ "					 FROM assessmentmaster a\r\n"
			+ "			            INNER JOIN modulemaster mo ON a.MODULEID=mo.MODULEID \r\n"
			+ "			             INNER JOIN submodulemaster sb ON a.SUBMODULEID=sb.SUBMODULEID\r\n"
			+ "			            INNER JOIN SCHEDULEFORMASTER sf ON a.SCHEDULEFORID = sf.SCHEDULEFORID \r\n"
			+ "                        \r\n"
			+ "					 WHERE a.DELETEDFLAG = 0 order by a.ASSESSMENTMASTERID  desc", nativeQuery = true)
	List<Map<String, Object>> getAllAssesmentdata();

	@Transactional
	@Modifying
	@Query(value = "update assessmentmaster set DELETEDFLAG=1 where ASSESSMENTMASTERID=:id", nativeQuery = true)
	void deleteAssessment(Integer id);
	
	@Query(value = "SELECT a.ASSESSMENTMASTERID , mo.MODULEID, mo.MODULENAME, sb.SUBMODULEID ,sb.SUBMODULENAME ,sf.SCHEDULEFOR,sf.SCHEDULEFORID, a.QUESTION, a.OPTION1, a.OPTION2,a.OPTION3,a.OPTION4,a.ANSWER\r\n"
			+ "			 FROM assessmentmaster a\r\n"
			+ "             INNER JOIN modulemaster mo ON a.MODULEID=mo.MODULEID \r\n"
			+ "             INNER JOIN submodulemaster sb ON a.SUBMODULEID=sb.SUBMODULEID\r\n"
			+ "             INNER JOIN SCHEDULEFORMASTER sf ON a.SCHEDULEFORID = sf.SCHEDULEFORID \r\n"
			+ "			 WHERE a.ASSESSMENTMASTERID = :id ", nativeQuery = true)
	Map<String, Object> updateAssessment(Integer id);
    
	
	
	
	// For upload Excel
	
	@Query(value = "SELECT m.MODULEID, m.MODULENAME" + " FROM modulemaster m"
			+ " WHERE m.DELETEDFLAG = 0", nativeQuery = true)
	List<Map<String, Object>> retriveModuleTypeList();

	@Query(value = "SELECT sm.SUBMODULEID, sm.MODULEID,sm.SUBMODULENAME" + " FROM submodulemaster sm"
			+ " WHERE sm.DELETEDFLAG = 0", nativeQuery = true)
	List<Map<String, Object>> retriveSubModuleList();

	@Query(value = "SELECT sf.SCHEDULEFORID, sf.SUBMODULEID,sf.SCHEDULEFOR" + " FROM scheduleformaster sf"
			+ " WHERE sf.DELETEDFLAG = 0", nativeQuery = true)
	List<Map<String, Object>> retriveScheduleForList();

	@Query(value = "SELECT ss.SESSIONID, ss.SUBMODULEID,ss.SESSIONNAME,ss.SCHEDULEFORID" + " FROM sessionmaster ss"
			+ " WHERE ss.DELETEDFLAG = 0", nativeQuery = true)
	List<Map<String, Object>> retriveSessionList();

	@Query(nativeQuery = true, value = "SELECT * FROM assessmentmaster WHERE SCHEDULEFORID = ?1 AND DELETEDFLAG = 0 ORDER BY RAND() LIMIT ?2")
	List<AssessmentMaster> getQuestionarByScheduleId(Integer scheduleId, Integer noOfQuestion);

}
