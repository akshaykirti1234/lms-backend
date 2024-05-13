package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.AuthorDto;
import com.csmtech.entity.Author;
import com.csmtech.service.AuthorService;

@CrossOrigin("*")
@RestController
public class AuthorController {
	private static final Logger logger=LoggerFactory.getLogger(AuthorController.class);

	@Autowired
	private AuthorService authorService;

	@PostMapping("/author")
	public ResponseEntity<Author> saveAuthor(@RequestBody AuthorDto authorDto) {
		logger.info("saveAuthor method of AuthorController is executed");
		Author authorMaster = authorService.saveAuthor(authorDto);
		return ResponseEntity.ok().body(authorMaster);
	}

	@GetMapping("/author")
	public ResponseEntity<List<Author>> getAllAuthor() {
		logger.info("getAllAuthor method of AuthorController is executed");
		List<Author> authorList = authorService.getAllAuthors();
		return ResponseEntity.ok().body(authorList);
	}

	@DeleteMapping("author/{auId}")
	public ResponseEntity<Map<String, Object>> deleteAuthor(@PathVariable("auId") Integer auId) {
		logger.info("deleteAuthor method of AuthorController is executed");
		Map<String, Object> response = new HashMap<>();
		authorService.deleteAuthor(auId);
		response.put("status", "Deleted");
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("author/{auId}")
	public ResponseEntity<Author> getAuthorById(@PathVariable("auId") Integer auId) {
		logger.info("getAuthorById method of AuthorController is executed");
		Author update = authorService.getAuthorById(auId);
		return ResponseEntity.ok().body(update);
	}
}
