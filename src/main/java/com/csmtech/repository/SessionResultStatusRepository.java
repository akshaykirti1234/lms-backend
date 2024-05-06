package com.csmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.SessionResultStatus;

@Repository
public interface SessionResultStatusRepository extends JpaRepository<SessionResultStatus, Integer> {

	@Modifying
	@Query(value = "DELETE FROM SessionResultStatus WHERE SESSIONID = :sessionId AND USERID = :userId", nativeQuery = true)
	void deleteResultStatusBySessionIdAndUserId(Integer sessionId, Integer userId);

	@Query(value = "SELECT * FROM sessionresultstatus srs " + "JOIN SESSIONMASTER sm ON srs.SESSIONID = sm.SESSIONID "
			+ "JOIN USERMASTER um ON srs.USERID = um.USERID "
			+ "WHERE sm.SESSIONID = :sessionId AND um.USERID = :userId "
			+ "ORDER BY srs.CREATEDON DESC LIMIT 1", nativeQuery = true)
	SessionResultStatus findSessionMasterBySessionIdAndUserId(Integer sessionId, Integer userId);
	
	@Query(value = "select * FROM SessionResultStatus WHERE USERID = :userId", nativeQuery = true)
	List<SessionResultStatus> getSessionResultStatus(Integer userId);

	@Query(value = "select * FROM SessionResultStatus WHERE SESSIONID = :sessionId and USERID = :userId", nativeQuery = true)
	List<SessionResultStatus> getSessionResultBySessionIdUserId(Integer sessionId, Integer userId);

}
