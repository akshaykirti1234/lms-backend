package com.csmtech.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csmtech.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
	@Transactional
	@Modifying
	@Query(value = "update locationmaster set DELETEDFLAG=1 where LOCATIONID=:id", nativeQuery = true)
	void deleteLocation(Integer id);

	@Query(value = "SELECT l.LOCATIONID, l.LOCATIONNAME " + "FROM locationmaster l "
			+ "WHERE l.DELETEDFLAG = 0", nativeQuery = true)
	List<Map<String, Object>> getAllLocation();

	@Query(value = "SELECT l.LOCATIONID, l.LOCATIONNAME " + "FROM locationmaster l "
			+ "WHERE l.DELETEDFLAG = 0 AND l.LOCATIONID = :id", nativeQuery = true)
	Map<String, Object> updateLocation(Integer id);

}
