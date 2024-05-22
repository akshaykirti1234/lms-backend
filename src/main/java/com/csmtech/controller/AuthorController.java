package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            logger.info("saveAuthor method of AuthorController is executed");
            Author authorMaster = authorService.saveAuthor(authorDto);
            return ResponseEntity.ok().body(authorMaster);
        } catch (Exception e) {
            logger.error("An error occurred while saving the author.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/author")
    public ResponseEntity<List<Author>> getAllAuthor() {
        try {
            logger.info("getAllAuthor method of AuthorController is executed");
            List<Author> authorList = authorService.getAllAuthors();
            return ResponseEntity.ok().body(authorList);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving all authors.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("author/{auId}")
    public ResponseEntity<Map<String, Object>> deleteAuthor(@PathVariable("auId") Integer auId) {
        try {
            logger.info("deleteAuthor method of AuthorController is executed");
            Map<String, Object> response = new HashMap<>();
            authorService.deleteAuthor(auId);
            response.put("status", "Deleted");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the author with ID: " + auId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("author/{auId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("auId") Integer auId) {
        try {
            logger.info("getAuthorById method of AuthorController is executed");
            Author update = authorService.getAuthorById(auId);
            return ResponseEntity.ok().body(update);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the author with ID: " + auId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
