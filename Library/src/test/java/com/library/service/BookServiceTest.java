package com.library.service;

import static org.mockito.Mockito.verify;

import com.library.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void newBook() {
    }

    @Test
    void findBook(Integer id) {//call findBook to prove that it works
        bookService.findBook(id);
        verify(bookRepository).findById(id);
    }

    @Test
    void editBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void findAllBooks() {
    }
}