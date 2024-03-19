package com.csmtech.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.csmtech.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

	@Query("from Author where deletedFlag = false")
	List<Author> getAllAuthors();

	@Transactional
	@Modifying
	@Query(value = "update author set DELETEDFLAG=1 where AUTHID=:auId", nativeQuery = true)
	void deleteAuthor(Integer auId);

}
