package com.csmtech.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.UserMaster;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Integer> {

	@Query("from UserMaster where emailId = :emailId AND deletedFlag = false")
	UserMaster getUserByEmail(String emailId);

	@Query("select emailId from UserMaster where deletedFlag = false")
	List<Object> getAllUsers();
	
	@Query(value="select emailid , normalpassword from usermaster where EMAILID=:email and DELETEDFLAG = 0", nativeQuery = true)
	Map<String, Object> getEmailPassword(String email);

}
