package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthorControllerTest {

    private MockMvc mvc;
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private AuthorController authorController;

    Author author1;

    List<Author> authors;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(authorController).build();
        author1 = new Author();
        author1.setId(1);
        author1.setName("Name1");
        author1.setSurname("Surname1");
        author1.setNationality("Nationality1");
        author1.setDateOfBirth("01/01/0001");
        authors = Arrays.asList(author1);


    }

    @Test
    void getAuthorById() throws Exception {
        when(authorService.findAuthor(1)).thenReturn(Optional.of(author1));

        mvc.perform(get("/api/authors/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.surname").value("Surname1"))
                .andExpect(jsonPath("$.nationality").value("Nationality1"))
                .andExpect(jsonPath("$.dateOfBirth").value("01/01/0001"));

        verify(authorService).findAuthor(1);

    }

    @Test
    void postAuthor() throws Exception{
        when(authorService.newAuthor(author1)).thenReturn(author1);

        mvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(author1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.surname").value("Surname1"))
                .andExpect(jsonPath("$.nationality").value("Nationality1"))
                .andExpect(jsonPath("$.dateOfBirth").value("01/01/0001"));

        verify(authorService).newAuthor(author1);
    }

    @Test
    void deleteAuthor() throws Exception{
        when(authorService.findAuthor(1)).thenReturn(Optional.of(author1));

        mvc.perform(delete("/api/authors/{id}", 1))
                .andExpect(status().isNoContent());

        verify(authorService).findAuthor(1);
        verify(authorService, times(1)).deleteAuthor(1);
    }

    @Test
    void deleteAuthorThatNotExist() throws Exception{
        when(authorService.findAuthor(1)).thenReturn(Optional.empty());

        mvc.perform(delete("/api/authors/{id}", 1))
                .andExpect(status().isNotFound());

        verify(authorService).findAuthor(1);
        verify(authorService, times(0)).deleteAuthor(1);
    }

    @Test
    void getAllAuthors() throws Exception {
        when(authorService.findAllAuthors(null, null)).thenReturn(authors);

        mvc.perform(get("/api/authors"))
                .andExpect(status().isOk());

        verify(authorService).findAllAuthors(null, null);
    }

    @Test
    void getAllAuthorsByNationality() throws Exception {
        when(authorService.findAllAuthors(null, "Nationality1")).thenReturn(authors);

        mvc.perform(get("/api/authors?nationality=Nationality1"))
                .andExpect(status().isOk());

        verify(authorService).findAllAuthors(null, "Nationality1");
        verify(authorService, times(0)).findAllAuthors(null, null);
    }

    @Test
    void getAllAuthorsBySurname() throws Exception {
        when(authorService.findAllAuthors("Surname1", null)).thenReturn(authors);

        mvc.perform(get("/api/authors?surname=Surname1"))
                .andExpect(status().isOk());

        verify(authorService).findAllAuthors("Surname1", null );
        verify(authorService, times(0)).findAllAuthors(null, null);
    }

    @Test
    void editAuthor()throws Exception {
        when(authorService.editAuthor(1, author1)).thenReturn(Optional.of(author1));

        mvc.perform(put("/api/authors/{id}",1)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(asJsonString(author1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.surname").value("Surname1"))
                .andExpect(jsonPath("$.nationality").value("Nationality1"))
                .andExpect(jsonPath("$.dateOfBirth").value("01/01/0001"));

        verify(authorService).editAuthor(1, author1);
    }

    @Test
    void getAuthorByNationality1() throws Exception {
        when(authorService.findAuthorByNationality1("Nationality1")).thenReturn(authors);

        mvc.perform(get("/api/authors/{nationality}", "Nationality1"))
                .andExpect(status().isOk());

        verify(authorService).findAuthorByNationality1("Nationality1");
    }

    @Test
    void getAuthorsByNationalityAndSurname() throws Exception {
        when(authorService.findAuthorByNationalityAndSurname("Nationality1", "Surname1"))
                .thenReturn(authors);

        mvc.perform(get("/api/authors/{nationality}/{surname}","Nationality1", "Surname1"))
                .andExpect(status().isOk());

        verify(authorService).findAuthorByNationalityAndSurname("Nationality1", "Surname1");
    }
    //Tests do not work and edit i want to learn to write in the right way
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}