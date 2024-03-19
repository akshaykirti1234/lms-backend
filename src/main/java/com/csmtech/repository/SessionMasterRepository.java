package com.csmtech.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.SessionMaster;

@Repository
public interface SessionMasterRepository extends JpaRepository<SessionMaster, Integer> {

	@Query(value = "SELECT ss.sessionid,ss.sessionname, sm.submodulename, sc.schedulefor, ss.vedio, ss.document, ss.sessiondescription\r\n"
			+ "FROM sessionmaster ss\r\n" + "JOIN submodulemaster sm ON ss.SUBMODULEID = sm.SUBMODULEID\r\n"
			+ "JOIN scheduleformaster sc ON ss.SCHEDULEFORID = sc.SCHEDULEFORID\r\n"
			+ "WHERE ss.deletedflag = 0;", nativeQuery = true)
	List<Map<String, Object>> getAllSessionMaster();

	@Modifying
	@Transactional
	@Query(value = "update sessionmaster set DELETEDFLAG=1 where sessionid=:id", nativeQuery = true)
	void deleteSession(Integer id);

	@Query(value = "select ISLASTSESSION \r\n" + "from SESSIONMASTER\r\n"
			+ "where SCHEDULEFORID=:id and DELETEDFLAG=0\r\n" + "ORDER BY ISLASTSESSION desc\r\n"
			+ "LIMIT 1 ;", nativeQuery = true)
	Boolean checkIsLastSession(Integer id);

	@Query(value = "select if(\r\n" + "(select SUM(NOOFSESSION)\r\n" + "FROM SCHEDULEFORMASTER\r\n"
			+ "WHERE SCHEDULEFORID =:id and DELETEDFLAG = 0)\r\n" + "=\r\n" + "(select count(SESSIONID)\r\n"
			+ "from SESSIONMASTER\r\n" + "where SCHEDULEFORID =:id and DELETEDFLAG = 0)+1\r\n"
			+ ", 'true', 'false') Status;\r\n" + "", nativeQuery = true)
	String checkBoxValidation(Integer id);

}
