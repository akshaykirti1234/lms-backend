package com.csmtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.TopicMaster;

@Repository
public interface TopicMasterRepository extends JpaRepository<TopicMaster, Integer> {

	/***
	 * @author akshaykirti.muduli
	 */
	@Query("from TopicMaster where userMaster.userId = :userId and scheduleForMaster.scheduleForId = :scheduleForId ORDER BY topicId DESC")
	TopicMaster getTopicByUserIdAndScheduleId(Integer userId, Integer scheduleForId);

}
