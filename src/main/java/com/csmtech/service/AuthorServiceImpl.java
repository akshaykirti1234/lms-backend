package com.csmtech.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.dto.AuthorDto;
import com.csmtech.entity.Author;
import com.csmtech.repository.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public List<Author> getAllAuthors() {
		return authorRepository.getAllAuthors();
	}

	@Override
	public Author saveAuthor(AuthorDto authorDto) {
		Author authorEnt = new Author();
		authorEnt.setAuthId(authorDto.getAuthId());
		authorEnt.setAuthName(authorDto.getAuthName());
		authorEnt.setEmail(authorDto.getEmail());
		authorEnt.setPhone(authorDto.getPhone());
		authorEnt.setCreatedOn(new Date());
		authorEnt.setCreatedBy(1);
		authorEnt.setUpdatedOn(new Date());
		authorEnt.setUpdatedBy(1);
		authorEnt.setDeletedFlag(false);
		return authorRepository.save(authorEnt);
	}

	@Override
	public void deleteAuthor(Integer auId) {
		authorRepository.deleteAuthor(auId);
	}

	@Override
	public Author getAuthorById(Integer auId) {
		return authorRepository.findById(auId).get();
	}

}
