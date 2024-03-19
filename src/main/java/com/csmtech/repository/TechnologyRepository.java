package com.csmtech.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.Technology;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Integer> {

	@Query("from Technology where deletedFlag = false")
	List<Technology> getAllTechnologies();

	@Transactional
	@Modifying
	@Query(value = "update technologymaster set DELETEDFLAG=1 where TECHID=:tecId", nativeQuery = true)
	void deleteTechnology(Integer tecId);

}
