package com.csmtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.TopicMaster;

@Repository
public interface TopicMasterRepository extends JpaRepository<TopicMaster, Integer> {

}
