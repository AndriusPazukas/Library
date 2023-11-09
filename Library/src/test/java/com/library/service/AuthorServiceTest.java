package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import com.library.specifications.AuthorSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    Author author1, author2, author3, author4, author5, savedAuthor1;

    List<Author> authors;

    @BeforeEach
    void setUp() {
        author1 = new Author("name1","surname1","date1", "nationality1");
        author2 = new Author("name2","surname2","date2", "nationality2");
        author3 = new Author("name3","surname3","date3", "nationality3");
        author4 = new Author("name4","surname4","date4", "nationality4");
        author5 = new Author("name5","surname5","date5", "nationality5");
        savedAuthor1 = new Author("name1","surname1","date1", "nationality1");
        savedAuthor1.setId(1);
        authors = Arrays.asList(author1,author2,author3,author4,author5);
    }

    @AfterEach //Para que sirve
    void tearDown() {
    }

    @Test
    void newAuthor() {
        when(authorRepository.save(author1)).thenReturn(savedAuthor1);
        //when
        Author result = authorService.newAuthor(author1);
        //then
        assertEquals(result, savedAuthor1);
        verify(authorRepository).save(author1);
    }

    @Test
    void findAuthor() {
        Optional<Author> authorOptional1 = Optional.of(author1);
        when(authorRepository.findById(1)).thenReturn(authorOptional1);
        //when
        Optional<Author> result = authorService.findAuthor(1);
        //then
        assertEquals(result, authorOptional1);
        verify(authorRepository).findById(1);
        verify(authorRepository, times (0)).findAll();
    }

    @Test
    void deleteAuthor() {
        Optional<Author> authorOptional1 = Optional.of(author1);
        when(authorRepository.findById(1)).thenReturn(authorOptional1);
        //when
        authorService.deleteAuthor(1);
        //then
        verify(authorRepository).findById(1);
        verify(authorRepository).deleteById(1);
    }

    @Test
    void deleteAuthorThatDoesNotExists(){
        Optional<Author> authorOptional1 = Optional.empty();
        when(authorRepository.findById(1)).thenReturn(authorOptional1);
        //when
        authorService.deleteAuthor(1);
        //then
        verify(authorRepository).findById(1);
        verify(authorRepository, times(0)).deleteById(1);
    }

    @Test
    void findAllAuthors() {
        when(authorRepository.findAll()).thenReturn(authors);
        //when
        List<Author>result = authorService.findAllAuthors(null,null);
        //then
        assertEquals(result, authors );
        verify(authorRepository).findAll();
        verify(authorRepository,times(0)).findById(any());
    }

    @Test
    void findAuthorByNationality() {
        when(authorRepository.findByNationality("English")).thenReturn(authors);
        //when
        List<Author> result = authorService.findAllAuthors(null, "English");
        //then
        assertEquals(result, authors);
        verify(authorRepository).findByNationality("English");
        verify(authorRepository, times(0)).findAll();
        verify(authorRepository, times (0)).findBySurnameAndNationality(any(), any());//Queria testear pero no me da usar String con any
        verify(authorRepository, times(0)).findBySurname(any());

    }

    @Test
    void findAuthorBySurnameAndNationality() {
        when(authorRepository.findBySurnameAndNationality("Pazukas", "Lithuanian")).thenReturn(authors);
        //when
        List<Author> result = authorService.findAllAuthors("Pazukas", "Lithuanian");
        //then
        assertEquals(result, authors);
        verify(authorRepository, times(0)).findByNationality("Lithuanian");
        verify(authorRepository, times(0)).findAll();
        verify(authorRepository).findBySurnameAndNationality("Pazukas", "Lithuanian");//Queria testear pero no me da usar String con any
        verify(authorRepository, times(0)).findBySurname("Pazukas");

    }

    @Test
    void editAuthor() {
        when(authorRepository.findById(1)).thenReturn(Optional.of(author1));
        when(authorRepository.save(any())).thenReturn(author1);
        //when
        Author editedAuthor = new Author("EditedName", "EditedSurname", "EditedDate", "EditedNationality");
        Optional<Author> result = authorService.editAuthor(1,editedAuthor);
        //then
        assertEquals(result, Optional.of(author1));
        assertEquals(result.get().getName(), "EditedName");
        assertEquals(result.get().getSurname(), "EditedSurname");
        assertEquals(result.get().getDateOfBirth(),"EditedDate");
        assertEquals(result.get().getNationality(),"EditedNationality");
        verify(authorRepository).findById(1);
        verify(authorRepository).save(author1);

    }
    @Test
    void findAuthorByNationalityAndSurname(){
        Specification<Author> input = AuthorSpecification.getAuthorByNatAndSurname("Lithuanian", "Pazukas");
        when(authorRepository.findAll(input)).thenReturn(any());
        //when
        List<Author> result = authorService.findAuthorByNationalityAndSurname("Lithuanian", "Pazukas");
        //then
        assertEquals(result, authors);
        verify(authorRepository).findAll(any(Specification.class));
        verify(authorRepository, times (0)).findBySurnameAndNationality("Pazukas", "Lithuanian");

    }

    @Test
    void testFindAuthorByNationalityAndSurname() {

    }
}