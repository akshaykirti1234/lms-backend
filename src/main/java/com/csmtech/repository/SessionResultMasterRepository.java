package com.csmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.csmtech.entity.SessionResultMaster;

public interface SessionResultMasterRepository extends JpaRepository<SessionResultMaster, Integer> {

	@Query("from SessionResultMaster where sessionMaster.sessionId = :sessionId and userMaster.userId = :userId")
	List<SessionResultMaster> getResultBySessionIdAndUserID(Integer sessionId, Integer userId);

	@Modifying
	@Query(value = "DELETE FROM sessionresultmaster WHERE SESSIONID = :sessionId AND USERID = :userId", nativeQuery = true)
	void deleteResultBySessionIdAndUserId(@Param("sessionId") Integer sessionId, @Param("userId") Integer userId);

}
