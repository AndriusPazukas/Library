package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class BookControllerTest {

    private MockMvc mvc;
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;

    Book book1,book2,book3;
    List books;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(bookController).build();
        book1= new Book();
        book1.setId(1);
        book1.setTitle("Title1");
        book1.setAuthor(new Author("Name1","Surname1","DateofBirth", "nationality1"));
        book1.setPrice(BigDecimal.TEN);

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
    void postBook() throws Exception{//controlar si es bueno
        when(bookService.newBook(book1)).thenReturn(book1);

        mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Title1"));

        verify(bookService).newBook(book1);
    }

    @Test
    void deleteBook() throws Exception {
        when(bookService.findBook(1)).thenReturn(Optional.of(book1));
        //bookService.deleteBook(1);

        mvc.perform(delete("/api/books/{id}",1))
                .andExpect(status().isNoContent());

        verify(bookService).findBook(1);
        verify(bookService,times(1)).deleteBook(1);//once at BookController, once at BookControllerTest
    }

    @Test
    void deleteBookThatDoesNotExists() throws Exception {
        when(bookService.findBook(2)).thenReturn(Optional.empty());

        mvc.perform(delete("/api/books/{id}",2))
                .andExpect(status().isNotFound());

        verify(bookService).findBook(2);
    }

    @Test
    void editBook() throws Exception{
        Book editedBook = new Book("EditedTitle", any(),BigDecimal.valueOf(10.0), 10);
        editedBook.setId(1);
        when(bookService.findBook(1)).thenReturn(Optional.of(book1));
        when(bookService.editBook(1, book1)).thenReturn(Optional.of(editedBook));

        mvc.perform(put("/api/books/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book1)))//por que book1, y no editedBook
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("EditedTitle"))
                .andExpect(jsonPath("$.price").value(10.0))
                .andExpect(jsonPath("$.quantity").value(10));

        verify(bookService).editBook(1, book1);

    }

    @Test
    void editBookThatDoesNotExists() throws Exception {
        when(bookService.editBook(1, book1)).thenReturn(Optional.empty());

        mvc.perform(put("/api/books/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book1)))
                .andExpect(status().isNotFound());

        verify(bookService).editBook(1, book1);
    }

    @Test
    void getAllBooks() throws Exception{
        when(bookService.findAllBooks(null,null, null,null))
                .thenReturn(books);

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk());

        verify(bookService).findAllBooks(null,null, null,null);
    }


    @Test
    void getAllBooksByAuthorSurname() throws Exception{//test passed but actualy method return all books in Postman, not only from one Author
        when(bookService.findAllBooks("Surname1",null, null,null))
                .thenReturn(books);

        mvc.perform(get("/api/books?authorSurname=Surname1"))
                .andExpect(status().isOk());

        verify(bookService).findAllBooks("Surname1",null, null,null);
    }
    //tests getAllBooks only test that api is ok, that method doesn't exist/work in bookService or bookRepository they doesn't show?
    @Test
    void getAllBooksByPriceOrder() throws Exception{
        when(bookService.findAllBooks(null,null, null,"ASC"))
                .thenReturn(books);

        mvc.perform(get("/api/books?priceOrder=ASC"))
                .andExpect(status().isOk());

        verify(bookService).findAllBooks(null,null, null,"ASC");
    }

    @Test
    void getAllBooksByMinPrice() throws Exception{
        when(bookService.findAllBooks(null,BigDecimal.ONE, null,null))
                .thenReturn(books);

        mvc.perform(get("/api/books?minPrice=1"))
                .andExpect(status().isOk());

        verify(bookService).findAllBooks(null,BigDecimal.ONE, null,null);
    }

    @Test
    void getAllBooksByMaxPrice() throws Exception{
        when(bookService.findAllBooks(null,null, BigDecimal.TEN,null))
                .thenReturn(books);

        mvc.perform(get("/api/books?maxPrice=10"))
                .andExpect(status().isOk());

        verify(bookService).findAllBooks(null,null, BigDecimal.TEN,null);
    }
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}