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

	@Query(value="select m.MODULENAME,s.SUBMODULENAME,sc.SCHEDULEFOR,a.NOOFQUESTION from assessmentsetting a\r\n"
			+ "join modulemaster m on a.MODULEID=m.MODULEID\r\n"
			+ "join submodulemaster s on a.SUBMODULEID=s.SUBMODULEID\r\n"
			+ "join scheduleformaster sc on a.SCHEDULEFORID=sc.SCHEDULEFORID \r\n"
			+ "where a.DELETEDFLAG=0;",nativeQuery=true)
	List<Map<String, Object>> getAssessmentSetting();
	
	
    @Query(value="select m.MODULENAME,s.SUBMODULENAME,sc.SCHEDULEFOR,a.NOOFQUESTION from assessmentsetting a\r\n"
    		+ "join modulemaster m on a.MODULEID=m.MODULEID\r\n"
    		+ "join submodulemaster s on a.SUBMODULEID=s.SUBMODULEID\r\n"
    		+ "join scheduleformaster sc on a.SCHEDULEFORID=sc.SCHEDULEFORID \r\n"
    		+ "where a.ASSESSMENTSETTINGID=:assessmentSettingId",nativeQuery=true)
	Map<String, Object> getAssessmentSettingById(Integer assessmentSettingId);

    
    @Modifying
   	@Transactional
   	@Query(value = "update assessmentsetting set NOOFQUESTION=:noOfQuestions where ASSESSMENTSETTINGID=:assessmentSettingId", nativeQuery = true)
   	void updateAssessmentSetting(Integer assessmentSettingId, Integer noOfQuestions);


    @Modifying
	@Transactional
	@Query(value = "update assessmentsetting set DELETEDFLAG=1 where ASSESSMENTSETTINGID=:assessmentSettingId", nativeQuery = true)
	void deleteAssessmentSetting(Integer assessmentSettingId);

   
}
