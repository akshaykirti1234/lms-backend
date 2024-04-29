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

	@Query(value = "SELECT a.ASSESSMENTMASTERID , mo.MODULEID, mo.MODULENAME, sb.SUBMODULEID ,sb.SUBMODULENAME ,sf.SCHEDULEFOR,a.SCHEDULEFORID, a.QUESTION, a.OPTION1, a.OPTION2,a.OPTION3,a.OPTION4,a.ANSWER\r\n"
			+ "			 FROM assessmentmaster a\r\n"
			+ "             INNER JOIN modulemaster mo ON a.MODULEID=mo.MODULEID \r\n"
			+ "             INNER JOIN submodulemaster sb ON a.SUBMODULEID=sb.SUBMODULEID\r\n"
			+ "             INNER JOIN SCHEDULEFORMASTER sf ON a.SCHEDULEFORID = sf.SCHEDULEFORID \r\n"
			+ "			 WHERE a.DELETEDFLAG = 0 ", nativeQuery = true)
	List<Map<String, Object>> getAllAssesmentdata();

	@Transactional
	@Modifying
	@Query(value = "update assessmentmaster set DELETEDFLAG=1 where ASSESSMENTMASTERID=:id", nativeQuery = true)
	void deleteAssessment(Integer id);
	
	@Query(value = "SELECT a.ASSESSMENTMASTERID , mo.MODULEID, mo.MODULENAME, sb.SUBMODULEID ,sb.SUBMODULENAME ,sf.SCHEDULEFOR,a.SCHEDULEFORID, a.QUESTION, a.OPTION1, a.OPTION2,a.OPTION3,a.OPTION4,a.ANSWER\r\n"
			+ "			 FROM assessmentmaster a\r\n"
			+ "             INNER JOIN modulemaster mo ON a.MODULEID=mo.MODULEID \r\n"
			+ "             INNER JOIN submodulemaster sb ON a.SUBMODULEID=sb.SUBMODULEID\r\n"
			+ "             INNER JOIN SCHEDULEFORMASTER sf ON a.SCHEDULEFORID = sf.SCHEDULEFORID \r\n"
			+ "			 WHERE a.ASSESSMENTMASTERID = :id ", nativeQuery = true)
	Map<String, Object> updateAssessment(Integer id);
    
	@Query(value="SELECT sa.SESSIONASSESSMENTMASTERID , mo.MODULEID, mo.MODULENAME , sm.SESSIONID, sm.SESSIONNAME, sb.SUBMODULEID ,sb.SUBMODULENAME , sf.SCHEDULEFOR,sf.SCHEDULEFORID,  sa.QUESTION, sa.OPTION1, sa.OPTION2,sa.OPTION3,sa.OPTION4,sa.ANSWER\r\n"
			+ "			 FROM sessionassessmentmaster sa\r\n"
			+ "             INNER JOIN modulemaster mo ON sa.MODULEID=mo.MODULEID \r\n"
			+ "             INNER JOIN submodulemaster sb ON sa.SUBMODULEID=sb.SUBMODULEID\r\n"
			+ "             INNER JOIN SCHEDULEFORMASTER sf ON sa.SCHEDULEFORID = sf.SCHEDULEFORID \r\n"
			+ "             INNER JOIN sessionmaster sm ON sa.SESSIONID=sm.SESSIONID\r\n"
			+ "			 WHERE sa.DELETEDFLAG = 0" , nativeQuery = true)
	List<Map<String, Object>> viewAssessmentForSessionData();
  
	@Transactional
	@Modifying
	@Query(value = "update sessionassessmentmaster set DELETEDFLAG=1 where SESSIONASSESSMENTMASTERID=:id", nativeQuery = true)
	void deleteAssessmentSession(Integer id);
    
	@Query(value="SELECT sa.SESSIONASSESSMENTMASTERID , mo.MODULEID, mo.MODULENAME , sm.SESSIONID, sm.SESSIONNAME, sb.SUBMODULEID ,sb.SUBMODULENAME , sf.SCHEDULEFOR,sf.SCHEDULEFORID,  sa.QUESTION, sa.OPTION1, sa.OPTION2,sa.OPTION3,sa.OPTION4,sa.ANSWER\r\n"
			+ "			 FROM sessionassessmentmaster sa\r\n"
			+ "             INNER JOIN modulemaster mo ON sa.MODULEID=mo.MODULEID \r\n"
			+ "             INNER JOIN submodulemaster sb ON sa.SUBMODULEID=sb.SUBMODULEID\r\n"
			+ "             INNER JOIN SCHEDULEFORMASTER sf ON sa.SCHEDULEFORID = sf.SCHEDULEFORID \r\n"
			+ "             INNER JOIN sessionmaster sm ON sa.SESSIONID=sm.SESSIONID\r\n"
			+ "			 WHERE sa.SESSIONASSESSMENTMASTERID = :id" , nativeQuery = true)
	Map<String, Object> getAssessmentSessionById(Integer id);

}
