package com.csmtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.ResultStatus;

@Repository
public interface ResultStatusRepository extends JpaRepository<ResultStatus, Integer> {

}
