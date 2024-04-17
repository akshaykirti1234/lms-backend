package com.csmtech.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.dto.AuthorDto;
import com.csmtech.entity.Author;
import com.csmtech.repository.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {

	Logger logger=LoggerFactory.getLogger(AuthorServiceImpl.class);
	
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public List<Author> getAllAuthors() {
		logger.info("getAllAuthors method of AuthorServiceImpl is executed");
		return authorRepository.getAllAuthors();
	}

	@Override
	public Author saveAuthor(AuthorDto authorDto) {
		logger.info("saveAuthor method of AuthorServiceImpl is executed");
		Author authorEnt = new Author();
		authorEnt.setAuthId(authorDto.getAuthId());
		authorEnt.setAuthName(authorDto.getAuthName());
		authorEnt.setEmail(authorDto.getEmail());
		authorEnt.setPhone(authorDto.getPhone());
		authorEnt.setCreatedBy(1);
		authorEnt.setUpdatedBy(1);
		return authorRepository.save(authorEnt);
	}

	@Override
	public void deleteAuthor(Integer auId) {
		logger.info("deleteAuthor method of AuthorServiceImpl is executed");
		authorRepository.deleteAuthor(auId);
	}

	@Override
	public Author getAuthorById(Integer auId) {
		logger.info("getAuthorById method of AuthorServiceImpl is executed");
		return authorRepository.findById(auId).get();
	}

}
