package com.csmtech.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.csmtech.dto.AuthorDto;
import com.csmtech.entity.Author;
import com.csmtech.service.AuthorService;

public class AuthorControllerTest {
	  @Mock
	    private AuthorService authorService;

	    @InjectMocks
	    private AuthorController authorController;

	    @Test
	    public void testSaveAuthor() {
	        // Arrange
	        MockitoAnnotations.initMocks(this); // Initialize mocks
	        AuthorDto authorDto = new AuthorDto();
	        // Set up authorDto if needed
	        
	        Author savedAuthor = new Author();
	        // Set up savedAuthor if needed
	        
	        when(authorService.saveAuthor(any(AuthorDto.class))).thenReturn(savedAuthor);

	        // Act
	        ResponseEntity<Author> responseEntity = authorController.saveAuthor(authorDto);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
	    }

		private AuthorDto any(Class<AuthorDto> class1) {
			return null;
		}
      
		 @Test
		    public void testGetAllAuthor() {
		        // Arrange
		        MockitoAnnotations.initMocks(this); // Initialize mocks
		        
		        List<Author> mockAuthorList = new ArrayList<>();
		        // Add mock author data if needed
		        
		        // Mock the behavior of authorService to return the mock author list
		        when(authorService.getAllAuthors()).thenReturn(mockAuthorList);

		        // Act
		        ResponseEntity<List<Author>> responseEntity = authorController.getAllAuthor();

		        // Assert
		        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
		        assertEquals(mockAuthorList, responseEntity.getBody()); // Check body content
		    }
}
