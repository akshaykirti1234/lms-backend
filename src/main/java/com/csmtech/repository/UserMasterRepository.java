package com.csmtech.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.UserMaster;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Integer> {

	@Query("from UserMaster where emailId = :emailId AND deletedFlag = false")
	UserMaster getUserByEmail(String emailId);

	@Query("select emailId from UserMaster where deletedFlag = false")
	List<Object> getAllUsers();

	@Query(value = "select emailid , normalpassword from usermaster where EMAILID=:email and DELETEDFLAG = 0", nativeQuery = true)
	Map<String, Object> getEmailPassword(String email);

//	===========================================================

	@Query(value = "select um.USERID,um.FULLNAME,um.CONTACTNO,um.EMAILID,um.DEPARTMENT,um.DESIGNATION,um.USERTYPEID,lm.LOCATIONID,lm.LOCATIONNAME from usermaster um\r\n"
			+ " join locationmaster lm on um.LOCATIONID=lm.LOCATIONID where um.DELETEDFLAG=0", nativeQuery = true)
	List<Map<String, Object>> getUseMasterList();

	@Modifying
	@Transactional
	@Query(value = "update usermaster set DELETEDFLAG=1 where USERID=:userId", nativeQuery = true)
	void deleteUserMaster(Integer userId);

	@Query(value = "select um.USERID,um.FULLNAME,um.UPASSWORD,um.NORMALPASSWORD,um.CONTACTNO,um.EMAILID,um.DEPARTMENT,um.DESIGNATION,um.USERTYPEID,lm.LOCATIONID,lm.LOCATIONNAME from usermaster um\r\n"
			+ " join locationmaster lm on um.LOCATIONID=lm.LOCATIONID where um.USERID=:userId", nativeQuery = true)
	Map<String, Object> getUserMasterById(Integer userId);

	@Query(value = "select EMAILID from usermaster", nativeQuery = true)
	List<Map<String, Object>> getEmailList();

	boolean existsByEmailId(String emailId);

	@Modifying
	@Transactional
	@Query("update UserMaster set normalPassword = :newPassword, password =:password where emailId =:emailId")
	void updatePassword(String emailId, String newPassword, String password);

}
