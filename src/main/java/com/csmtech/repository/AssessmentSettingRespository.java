package com.csmtech.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.AssessmentSetting;

@Repository
public interface AssessmentSettingRespository extends JpaRepository<AssessmentSetting, Integer> {

	@Query(value="select a.ASSESSMENTSETTINGID,m.MODULENAME,s.SUBMODULENAME,sc.SCHEDULEFOR,a.NOOFQUESTION,a.PASSINGPERCENTAGE from assessmentsetting a\r\n"
			+ "join modulemaster m on a.MODULEID=m.MODULEID\r\n"
			+ "join submodulemaster s on a.SUBMODULEID=s.SUBMODULEID\r\n"
			+ "join scheduleformaster sc on a.SCHEDULEFORID=sc.SCHEDULEFORID \r\n"
			+ "where a.DELETEDFLAG=0;",nativeQuery=true)
	List<Map<String, Object>> getAssessmentSetting();
	
	
    @Query(value="select a.ASSESSMENTSETTINGID, m.MODULENAME,s.SUBMODULENAME,sc.SCHEDULEFOR,a.NOOFQUESTION,a.PASSINGPERCENTAGE from assessmentsetting a\r\n"
    		+ "join modulemaster m on a.MODULEID=m.MODULEID\r\n"
    		+ "join submodulemaster s on a.SUBMODULEID=s.SUBMODULEID\r\n"
    		+ "join scheduleformaster sc on a.SCHEDULEFORID=sc.SCHEDULEFORID \r\n"
    		+ "where a.ASSESSMENTSETTINGID=:assessmentSettingId",nativeQuery=true)
	Map<String, Object> getAssessmentSettingById(Integer assessmentSettingId);

    
    @Modifying
   	@Transactional
   	@Query(value = "update assessmentsetting set NOOFQUESTION=:noOfQuestions , PASSINGPERCENTAGE =:passingPercentage where ASSESSMENTSETTINGID=:assessmentSettingId", nativeQuery = true)
   	void updateAssessmentSetting(Integer assessmentSettingId, Integer noOfQuestions , Double passingPercentage);


    @Modifying
	@Transactional
	@Query(value = "update assessmentsetting set DELETEDFLAG=1 where ASSESSMENTSETTINGID=:assessmentSettingId", nativeQuery = true)
	void deleteAssessmentSetting(Integer assessmentSettingId);


	@Query(value="select sm.SCHEDULEFORID as scheduleForId,sm.SCHEDULEFOR as scheduleForName from scheduleformaster sm where sm.SUBMODULEID=:submoduleId and sm.DELETEDFLAG=0 and sm.SCHEDULEFORID not in(select SCHEDULEFORID from assessmentsetting where DELETEDFLAG=0 )",nativeQuery=true)
	List<Map<String, Object>> getScheduleforAssessmentSetting(Integer submoduleId);

   
}
