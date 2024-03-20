package com.csmtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.UserMaster;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Integer> {

	@Query("from UserMaster where emailId = :emailId AND deletedFlag = false")
	UserMaster getUserByEmail(String emailId);

	@Query("from UserMaster where deletedFlag = false")
	List<UserMaster> getAllUsers();

}
