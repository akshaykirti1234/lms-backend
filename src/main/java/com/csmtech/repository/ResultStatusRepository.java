package com.csmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.ResultStatus;

@Repository
public interface ResultStatusRepository extends JpaRepository<ResultStatus, Integer> {

	
	@Query(value = "select * FROM resultstatus WHERE SCHEDULEFORID = :scheduleForId and USERID = :userId", nativeQuery = true)
	List<ResultStatus> getFinalResultByScheduleIdUserId(Integer scheduleForId, Integer userId);

}
