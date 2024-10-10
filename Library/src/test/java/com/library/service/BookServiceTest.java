package com.library.service;

import static org.mockito.Mockito.verify;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    Book book1, book2, book3, book4, book5, savedBook1;

    Author author1, author2;

    List<Book> books;

    @BeforeEach
    void setUp() {
        author1 = new Author();
        author2 = new Author();
        book1 = new Book("Title1", author1, BigDecimal.valueOf(20.0), 10 );
        book2 = new Book("Title2", author1, BigDecimal.valueOf(30.0), 15 );
        book3 = new Book("Title3", author2, BigDecimal.valueOf(10.0), 16 );
        book4 = new Book("Title4", author2, BigDecimal.valueOf(40.0), 18 );
        book5 = new Book("Title5", author2, BigDecimal.valueOf(50.0), 22 );
        savedBook1 = new Book("Title1", author1, BigDecimal.valueOf(20.0), 10 );
        savedBook1.setId(1);
        books = Arrays.asList(book1, book2, book3, book4, book5);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void newBook() {//mockear metodo save de bookRepository
        when(bookRepository.save(book1)).thenReturn(savedBook1);
        //when
        Book result = bookService.newBook(book1);
        //then
        assertEquals(savedBook1, result);
        verify(bookRepository).save(book1);
    }

    @Test
    void findBook() {
        Optional<Book> bookOptional1 = Optional.of(book1);
        when(bookRepository.findById(1)).thenReturn(bookOptional1);
        //when
        Optional<Book> result = bookService.findBook(1);
        //then
        assertEquals(bookOptional1, result);
        verify(bookRepository, times(1)).findById(1);

    }


    @Test
    void deleteBook() {
        Optional<Book> bookOptional1 = Optional.of(book1);
        when(bookRepository.findById(1)).thenReturn(bookOptional1);
        //when
        bookService.deleteBook(1);
        //then
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteBookDoesNotExists() {
        Optional<Book> bookOptional1 = Optional.empty();
        when(bookRepository.findById(1)).thenReturn(bookOptional1);
        //when
        bookService.deleteBook(1);
        //then
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(0)).deleteById(1);
    }

    @Test
    void findAllBooksWithAuthorSurnameAndPriceOrderAsc() {
        when(bookRepository.findByAuthorSurnamePriceAsc("Pazukas")).thenReturn(books);
        //when
        List<Book> result = bookService.findAllBooks("Pazukas",null ,null, "ASC");
        //then
        assertEquals(result, books);
        verify(bookRepository).findByAuthorSurnamePriceAsc("Pazukas");
        verify(bookRepository, times(0)).findAll();
    }

    @Test
    void findAllBooksWithAuthorAndPriceOrderDesc(){
        when(bookRepository.findByAuthorSurnamePriceDesc("Pazukas")).thenReturn(books);
        //when
        List<Book> result = bookService.findAllBooks("Pazukas",null,null,"DESC");
        //then
        assertEquals(result, books);
        verify(bookRepository).findByAuthorSurnamePriceDesc("Pazukas");
        verify(bookRepository, times(0)).findAll();
    }

    @Test
    void findAllBooksByMinPrice(){
        when(bookRepository.findByPrice1(BigDecimal.valueOf(20.0))).thenReturn(books);
        //when
        List<Book> result = bookService.findAllBooks(null, BigDecimal.valueOf(20.0),null, null);
        //then
        assertEquals(result, books);
        verify(bookRepository).findByPrice1(BigDecimal.valueOf(20.0));
        verify(bookRepository, times(0)).findAll();
        verify(bookRepository, times(0)).findByPrice(any(BigDecimal.class), any(BigDecimal.class));//solo con any() tambien vale

    }

    @Test
    void findAllBooksByMaxPrice(){
        when(bookRepository.findByPrice2(BigDecimal.valueOf(30.0))).thenReturn(books);
        //when
        List<Book> result = bookService.findAllBooks(null, null, BigDecimal.valueOf(30.0),null);
        //then
        assertEquals(result, books);
        verify(bookRepository).findByPrice2(BigDecimal.valueOf(30.0));
        verify(bookRepository, times(0)).findAll();
    }

    @Test
    void findAllBooksByMinAndMaxPrice(){
        when(bookRepository.findByPrice(BigDecimal.valueOf(10.0), BigDecimal.valueOf(40.0))).thenReturn(books);
        //when
        List<Book> result = bookService.findAllBooks(null, BigDecimal.valueOf(10.0), BigDecimal.valueOf(40.0),null);
        //then
        assertEquals(result, books);
        verify(bookRepository).findByPrice(BigDecimal.valueOf(10.0), BigDecimal.valueOf(40.0));
        verify(bookRepository,times(0)).findAll();
    }

    @Test
    void findAllBooks(){
        when(bookRepository.findAll()).thenReturn(books);
        //when
        List<Book> result = bookService.findAllBooks(null,null,null,null);
        //then
        assertEquals(result, books);
        verify(bookRepository).findAll();
    }

    @Test
    void editBook(){
        Optional<Book> bookOptional = Optional.of(book1);
        when(bookRepository.findById(1)).thenReturn(bookOptional);
        when(bookRepository.save(any())).thenReturn(book1);
        //when
        Book editedBook = new Book("TitleEdited", author2, BigDecimal.valueOf(30.0), 15   );
        Optional<Book> result = bookService.editBook(1, editedBook);
        //then
        assertEquals(result, bookOptional);
        assertEquals(result.get().getTitle(), "TitleEdited");
        assertEquals(result.get().getAuthor(), author2);
        assertEquals(result.get().getPrice(), BigDecimal.valueOf(30.0));
        assertEquals(result.get().getQuantity(), 15);
        verify(bookRepository).findById(1);
        verify(bookRepository).save(book1);
    }

    @Test
    void editBookIfNotPresent(){
        Optional<Book> bookOptional1 = Optional.empty();
        when(bookRepository.findById(1)).thenReturn(bookOptional1);
        //when
        Optional<Book> result = bookService.editBook(1,book1);
        //then
        assertEquals(result, bookOptional1);
        verify(bookRepository).findById(1);
        verify(bookRepository, times(0)).save(any());
    }
}