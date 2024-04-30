package com.csmtech.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.SessionAssessmentSetting;

@Repository
public interface SessionAssessmentSettingRepository extends JpaRepository<SessionAssessmentSetting, Integer> {

	@Query(value = "select se.SESSIONASSESSMENTSETTINGID,m.MODULENAME,s.SUBMODULENAME,sc.SCHEDULEFOR,ses.SESSIONNAME,se.NOOFQUESTION from sessionassessmentsetting se\r\n"
			+ "join modulemaster m on se.MODULEID=m.MODULEID\r\n"
			+ "join submodulemaster s on se.SUBMODULEID=s.SUBMODULEID\r\n"
			+ "join scheduleformaster sc on se.SCHEDULEFORID=sc.SCHEDULEFORID \r\n"
			+ "join sessionmaster ses on se.SESSIONID=ses.SESSIONID\r\n"
			+ "where se.DELETEDFLAG=0;", nativeQuery = true)
	List<Map<String, Object>> getSessionAssessmentSetting();

	@Query(value = "se.SESSIONASSESSMENTSETTINGID,select m.MODULENAME,s.SUBMODULENAME,sc.SCHEDULEFOR,ses.SESSIONNAME,se.NOOFQUESTION from sessionassessmentsetting se\r\n"
			+ "join modulemaster m on se.MODULEID=m.MODULEID\r\n"
			+ "join submodulemaster s on se.SUBMODULEID=s.SUBMODULEID\r\n"
			+ "join scheduleformaster sc on se.SCHEDULEFORID=sc.SCHEDULEFORID \r\n"
			+ "join sessionmaster ses on se.SESSIONID=ses.SESSIONID\r\n"
			+ "where se.SESSIONASSESSMENTSETTINGID=:sessionAssessmentSettingId", nativeQuery = true)
	Map<String, Object> getSessionAssessmentSettingById(Integer sessionAssessmentSettingId);

	@Modifying
	@Transactional
	@Query(value = "update sessionassessmentsetting set DELETEDFLAG=1 where SESSIONASSESSMENTSETTINGID=:sessionAssessmentSettingId", nativeQuery = true)
	void deleteSessionAssessmentSetting(Integer sessionAssessmentSettingId);

	@Modifying
	@Transactional
	@Query(value = "update sessionassessmentsetting set NOOFQUESTION=:noOfQuestions where SESSIONASSESSMENTSETTINGID=:assessmentSettingId", nativeQuery = true)
	void updateSessionAssessmentSetting(Integer assessmentSettingId, Integer noOfQuestions);

	SessionAssessmentSetting findFirst1BySessionMaster_SessionId(Integer sessionId);

//	@Query(value = "select se.SESSIONASSESSMENTSETTINGID,m.MODULENAME,s.SUBMODULENAME,sc.SCHEDULEFOR,ses.SESSIONNAME,se.NOOFQUESTION from sessionassessmentsetting se\r\n"
//			+ "join modulemaster m on se.MODULEID=m.MODULEID\r\n"
//			+ "join submodulemaster s on se.SUBMODULEID=s.SUBMODULEID\r\n"
//			+ "join scheduleformaster sc on se.SCHEDULEFORID=sc.SCHEDULEFORID \r\n"
//			+ "join sessionmaster ses on se.SESSIONID=ses.SESSIONID\r\n"
//			+ "where se.DELETEDFLAG=0;", nativeQuery = true)
//	List<Map<String, Object>> getSessionAssessmentSetting();
//
//	@Query(value = "se.SESSIONASSESSMENTSETTINGID,select m.MODULENAME,s.SUBMODULENAME,sc.SCHEDULEFOR,ses.SESSIONNAME,se.NOOFQUESTION from sessionassessmentsetting se\r\n"
//			+ "join modulemaster m on se.MODULEID=m.MODULEID\r\n"
//			+ "join submodulemaster s on se.SUBMODULEID=s.SUBMODULEID\r\n"
//			+ "join scheduleformaster sc on se.SCHEDULEFORID=sc.SCHEDULEFORID \r\n"
//			+ "join sessionmaster ses on se.SESSIONID=ses.SESSIONID\r\n"
//			+ "where se.SESSIONASSESSMENTSETTINGID=:sessionAssessmentSettingId", nativeQuery = true)
//	Map<String, Object> getSessionAssessmentSettingById(Integer sessionAssessmentSettingId);
//
//	@Modifying
//	@Transactional
//	@Query(value = "update sessionassessmentsetting set DELETEDFLAG=1 where SESSIONASSESSMENTSETTINGID=:sessionAssessmentSettingId", nativeQuery = true)
//	void deleteSessionAssessmentSetting(Integer sessionAssessmentSettingId);
//
//	@Modifying
//	@Transactional
//	@Query(value = "update sessionassessmentsetting set NOOFQUESTION=:noOfQuestions where SESSIONASSESSMENTSETTINGID=:assessmentSettingId", nativeQuery = true)
//	void updateSessionAssessmentSetting(Integer assessmentSettingId, Integer noOfQuestions);

}