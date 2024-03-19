package com.csmtech.service;

import java.util.List;

import com.csmtech.dto.AuthorDto;
import com.csmtech.entity.Author;

public interface AuthorService {

	List<Author> getAllAuthors();

	Author saveAuthor(AuthorDto authorDto);

	void deleteAuthor(Integer auId);

	Author getAuthorById(Integer auId);

}
