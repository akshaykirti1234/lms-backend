package com.csmtech.repository;

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

	SessionResultStatus findBySessionMaster_SessionIdAndUserMaster_UserId(Integer sessionId, Integer userId);

}
