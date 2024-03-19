package com.csmtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.NotifyMaster;

@Repository
public interface NotifyMasterRepository extends JpaRepository<NotifyMaster, Integer> {

}
