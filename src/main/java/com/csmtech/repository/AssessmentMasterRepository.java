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

	@Query(value = "SELECT a.ASSESSMENTID, sf.SCHEDULEFOR,a.SCHEDULEFORID, a.QUESTION, a.OPTION1, a.OPTION2,a.OPTION3,a.OPTION4,a.ANSWER,a.CREATEDBY,a.CREATEDON,a.UPDATEDBY,a.UPDATEDON "
			+ " FROM assessmentmaster a " + " INNER JOIN SCHEDULEFORMASTER sf ON a.SCHEDULEFORID = sf.SCHEDULEFORID "
			+ " WHERE a.DELETEDFLAG = 0", nativeQuery = true)
	List<Map<String, Object>> getAllAssesmentdata();

	@Transactional
	@Modifying
	@Query(value = "update assessmentmaster set DELETEDFLAG=1 where ASSESSMENTID=:id", nativeQuery = true)
	void deleteAssessment(Integer id);

	@Query(value = "SELECT a.ASSESSMENTID, sf.SCHEDULEFOR, a.SCHEDULEFORID, a.QUESTION, a.OPTION1, a.OPTION2,a.OPTION3,a.OPTION4,a.ANSWER,a.CREATEDBY,a.CREATEDON,a.UPDATEDBY,a.UPDATEDON "
			+ " FROM assessmentmaster a " + " INNER JOIN SCHEDULEFORMASTER sf ON a.SCHEDULEFORID = sf.SCHEDULEFORID "
			+ "where a.ASSESSMENTID=:id", nativeQuery = true)
	Map<String, Object> updateAssessment(Integer id);

}
