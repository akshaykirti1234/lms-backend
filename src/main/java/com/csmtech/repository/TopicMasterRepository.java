package com.csmtech.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.TopicMaster;

@Repository
public interface TopicMasterRepository extends JpaRepository<TopicMaster, Integer> {

	@Query("from TopicMaster where deletedFlag = false")
	List<TopicMaster> viewTopicData();

	@Transactional
	@Modifying
	@Query(value = "update topic set deletedFlag=1 where TOPICID=:topicId", nativeQuery = true)
	void deleteTopic(Integer topicId);

}
