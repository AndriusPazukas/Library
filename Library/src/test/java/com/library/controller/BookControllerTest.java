package com.library.controller;

import com.library.entity.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class BookControllerTest {

    private MockMvc mvc;
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;

    Book book1;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(bookController).build();
        book1= new Book();
        book1.setId(1);
        book1.setTitle("Title1");

    }

    @Test
    void getBookById() throws Exception{
        when(bookService.findBook(1)).thenReturn(Optional.of(book1));

        mvc.perform(get("/api/books/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Title1"));

        verify(bookService).findBook(1);

    }

    @Test
    void postBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void editBook() {
    }

    @Test
    void getAllBooks() {
    }
}