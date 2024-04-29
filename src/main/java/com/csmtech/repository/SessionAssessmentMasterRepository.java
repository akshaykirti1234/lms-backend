package com.csmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.SessionAssessmentMaster;

@Repository
public interface SessionAssessmentMasterRepository extends JpaRepository<SessionAssessmentMaster, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM sessionassessmentmaster WHERE SESSIONID = ?1 ORDER BY RAND() LIMIT ?2")
	List<SessionAssessmentMaster> getQuestionarBySessionId(Integer sessionId, Integer noOfQuestion);

}
