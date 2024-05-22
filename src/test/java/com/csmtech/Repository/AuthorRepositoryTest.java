package com.csmtech.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.csmtech.entity.Author;
import com.csmtech.repository.AuthorRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTest {
	
	@Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setUp() {
        // Add initial data to the in-memory database
        Author author1 = new Author();
        author1.setAuthName("Author One");
        author1.setEmail("author1@example.com");
        author1.setPhone("1234567890");
        author1.setCreatedBy(1);
        author1.setUpdatedBy(1);
        
        Author author2 = new Author();
        author2.setAuthName("Author Two");
        author2.setEmail("author2@example.com");
        author2.setPhone("0987654321");
        author2.setCreatedBy(1);
        author2.setUpdatedBy(1);
        
        Author author3 = new Author();
        author3.setAuthName("Author Three");
        author3.setEmail("author3@example.com");
        author3.setPhone("1122334455");
        author3.setCreatedBy(1);
        author3.setUpdatedBy(1);
        
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> authors = authorRepository.getAllAuthors();
        assertThat(authors).isNotNull().hasSizeGreaterThan(1);  // Only two authors have deletedFlag = false
    }

    @Test
    @Transactional
    public void testDeleteAuthor() {
        authorRepository.deleteAuthor(1);
        List<Author> authors = authorRepository.findAll();
        Author deletedAuthor = authors.stream().filter(a -> a.getAuthId() == 1).findFirst().orElse(null);
        assertThat(deletedAuthor).isNotNull();
    }
    @Test
    public void testFindByAuthorId() {
        // Test for existing and non-deleted author
        Author author = authorRepository.findById(1).orElse(null);
        assertThat(author).isNotNull();
        assertThat(author.getAuthName()).isEqualTo("Saroj Kumar Mohanty");

        // Test for deleted author
        Author deletedAuthor = authorRepository.findById(25).orElse(null);
        assertThat(deletedAuthor).isNull();

        // Test for non-existing author
        Author nonExistingAuthor = authorRepository.findById(999).orElse(null);
        assertThat(nonExistingAuthor).isNull();
    }

}
