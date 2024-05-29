package com.csmtech.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.SessionAssessmentMaster;
import com.csmtech.entity.SessionMaster;

@Repository
public interface SessionAssessmentMasterRepository extends JpaRepository<SessionAssessmentMaster, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM sessionassessmentmaster WHERE SESSIONID = ?1 AND DELETEDFLAG = 0 ORDER BY RAND() LIMIT ?2")
	List<SessionAssessmentMaster> getQuestionarBySessionId(Integer sessionId, Integer noOfQuestion);

	@Query(value = "SELECT ss.sessionid,ss.sessionname, sm.submodulename, sc.schedulefor, ss.vedio, ss.document, ss.sessiondescription\r\n"
			+ "FROM sessionmaster ss\r\n" + "JOIN submodulemaster sm ON ss.SUBMODULEID = sm.SUBMODULEID\r\n"
			+ "JOIN scheduleformaster sc ON ss.SCHEDULEFORID = sc.SCHEDULEFORID\r\n"
			+ "WHERE ss.deletedflag = 0;", nativeQuery = true)
	List<Map<String, Object>> getAllSessionMaster();

	@Modifying
	@Transactional
	@Query(value = "update sessionmaster set DELETEDFLAG=1 where sessionid=:id", nativeQuery = true)
	void deleteSession(Integer id);

	@Query(value = "select ISLASTSESSION \r\n" + "from SESSIONMASTER\r\n"
			+ "where SCHEDULEFORID=:id and DELETEDFLAG=0\r\n" + "ORDER BY ISLASTSESSION desc\r\n"
			+ "LIMIT 1 ;", nativeQuery = true)
	Boolean checkIsLastSession(Integer id);

	@Query(value = "select if(\r\n" + "(select SUM(NOOFSESSION)\r\n" + "FROM SCHEDULEFORMASTER\r\n"
			+ "WHERE SCHEDULEFORID =:id and DELETEDFLAG = 0)\r\n" + "=\r\n" + "(select count(SESSIONID)\r\n"
			+ "from SESSIONMASTER\r\n" + "where SCHEDULEFORID =:id and DELETEDFLAG = 0)+1\r\n"
			+ ", 'true', 'false') Status;\r\n" + "", nativeQuery = true)
	String checkBoxValidation(Integer id);

	@Query("From SessionMaster where scheduleFor.scheduleForId = :scheduleId and DELETEDFLAG = 0")
	List<SessionMaster> getSessionByScheduleId(Integer scheduleId);

	@Query(value = "SELECT sa.SESSIONASSESSMENTMASTERID , mo.MODULEID, mo.MODULENAME , sm.SESSIONID, sm.SESSIONNAME, sb.SUBMODULEID ,sb.SUBMODULENAME , sf.SCHEDULEFOR,sf.SCHEDULEFORID,  sa.QUESTION, sa.OPTION1, sa.OPTION2,sa.OPTION3,sa.OPTION4,sa.ANSWER\r\n"
			+ "			 FROM sessionassessmentmaster sa\r\n"
			+ "             INNER JOIN modulemaster mo ON sa.MODULEID=mo.MODULEID \r\n"
			+ "             INNER JOIN submodulemaster sb ON sa.SUBMODULEID=sb.SUBMODULEID\r\n"
			+ "             INNER JOIN SCHEDULEFORMASTER sf ON sa.SCHEDULEFORID = sf.SCHEDULEFORID \r\n"
			+ "             INNER JOIN sessionmaster sm ON sa.SESSIONID=sm.SESSIONID\r\n"
			+ "			 WHERE sa.DELETEDFLAG = 0 order by sa.SESSIONASSESSMENTMASTERID  desc", nativeQuery = true)
	List<Map<String, Object>> viewAssessmentForSessionData();

	@Transactional
	@Modifying
	@Query(value = "update sessionassessmentmaster set DELETEDFLAG=1 where SESSIONASSESSMENTMASTERID=:id", nativeQuery = true)
	void deleteAssessmentSession(Integer id);

	@Query(value = "SELECT sa.SESSIONASSESSMENTMASTERID , mo.MODULEID, mo.MODULENAME , sm.SESSIONID, sm.SESSIONNAME, sb.SUBMODULEID ,sb.SUBMODULENAME , sf.SCHEDULEFOR,sf.SCHEDULEFORID,  sa.QUESTION, sa.OPTION1, sa.OPTION2,sa.OPTION3,sa.OPTION4,sa.ANSWER\r\n"
			+ "			 FROM sessionassessmentmaster sa\r\n"
			+ "             INNER JOIN modulemaster mo ON sa.MODULEID=mo.MODULEID \r\n"
			+ "             INNER JOIN submodulemaster sb ON sa.SUBMODULEID=sb.SUBMODULEID\r\n"
			+ "             INNER JOIN SCHEDULEFORMASTER sf ON sa.SCHEDULEFORID = sf.SCHEDULEFORID \r\n"
			+ "             INNER JOIN sessionmaster sm ON sa.SESSIONID=sm.SESSIONID\r\n"
			+ "			 WHERE sa.SESSIONASSESSMENTMASTERID = :id", nativeQuery = true)
	Map<String, Object> getAssessmentSessionById(Integer id);

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

	
	@Query(value="SELECT \r\n"
			+ "    CASE\r\n"
			+ "        WHEN \r\n"
			+ "            (SELECT NOOFSESSION FROM scheduleformaster WHERE SCHEDULEFORID = :id) != \r\n"
			+ "            (SELECT COUNT(DISTINCT sessionid) FROM sessionassessmentmaster WHERE SCHEDULEFORID = :id)\r\n"
			+ "            AND \r\n"
			+ "            (SELECT NOOFSESSION FROM scheduleformaster WHERE SCHEDULEFORID = :id) > \r\n"
			+ "            (SELECT COUNT(DISTINCT sessionid) FROM sessionassessmentmaster WHERE SCHEDULEFORID = :id)\r\n"
			+ "        THEN 'false'\r\n"
			+ "        WHEN \r\n"
			+ "            (SELECT NOOFSESSION FROM scheduleformaster WHERE SCHEDULEFORID = :id) = \r\n"
			+ "            (SELECT COUNT(DISTINCT sessionid) FROM sessionassessmentmaster WHERE SCHEDULEFORID = :id)\r\n"
			+ "        THEN 'true'\r\n"
			+ "    END AS result;" , nativeQuery = true)
	Map<String, Object> checkIfSessionQsnPreparedForScheduleId(Integer id);

}
