package com.csmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.ResultMaster;

@Repository
public interface ResultMasterRepository extends JpaRepository<ResultMaster, Integer> {

	
	
	@Modifying
	@Query(value = "DELETE FROM resultmaster WHERE SCHEDULEFORID = :scheduleForId AND USERID = :userId", nativeQuery = true)
	void deleteResultByScheduleIdAndUserId(@Param("scheduleForId") Integer scheduleForId, @Param("userId") Integer userId);


	@Query("from ResultMaster where scheduleForMaster.scheduleForId = :scheduleForId and userMaster.userId = :userId")
	List<ResultMaster> getResultByScheduleIdAndUserID(Integer scheduleForId, Integer userId);

}
