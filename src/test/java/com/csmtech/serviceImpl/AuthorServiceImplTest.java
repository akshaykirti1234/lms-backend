package com.csmtech.serviceImpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

import com.csmtech.dto.AuthorDto;
import com.csmtech.entity.Author;
import com.csmtech.repository.AuthorRepository;
import com.csmtech.service.AuthorServiceImpl;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private Logger logger;

    private AuthorDto authorDto;
    private Author author;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authorDto = new AuthorDto();
        authorDto.setAuthId(1);
        authorDto.setAuthName("Test Author");
        authorDto.setEmail("test@example.com");
        authorDto.setPhone("1234567890");

        author = new Author();
        author.setAuthId(1);
        author.setAuthName("Test Author");
        author.setEmail("test@example.com");
        author.setPhone("1234567890");
        author.setCreatedBy(1);
        author.setUpdatedBy(1);
    }

    @Test
    public void testGetAllAuthors_Success() {
        List<Author> authorList = new ArrayList<>();
        authorList.add(author);

        when(authorRepository.getAllAuthors()).thenReturn(authorList);

        List<Author> result = authorService.getAllAuthors();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Author", result.get(0).getAuthName());
        verify(authorRepository, times(1)).getAllAuthors();
    }

    @Test
    public void testSaveAuthor_Success() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author result = authorService.saveAuthor(authorDto);
        assertNotNull(result);
        assertEquals("Test Author", result.getAuthName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    public void testSaveAuthor_Failure() {
        doThrow(new RuntimeException("Database Error")).when(authorRepository).save(any(Author.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authorService.saveAuthor(authorDto);
        });

        assertEquals("Database Error", exception.getMessage());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    public void testDeleteAuthor_Success() {
        doNothing().when(authorRepository).deleteAuthor(anyInt());

        authorService.deleteAuthor(1);
        verify(authorRepository, times(1)).deleteAuthor(1);
    }

    @Test
    public void testGetAuthorById_Success() {
        when(authorRepository.findById(anyInt())).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorById(1);
        assertNotNull(result);
        assertEquals(1, result.getAuthId());
        assertEquals("Test Author", result.getAuthName());
        verify(authorRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAuthorById_NotFound() {
        when(authorRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            authorService.getAuthorById(1);
        });

        assertEquals("No value present", exception.getMessage());
        verify(authorRepository, times(1)).findById(1);
    }
}
