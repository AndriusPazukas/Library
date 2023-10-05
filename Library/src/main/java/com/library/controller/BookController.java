package com.library.controller;

import com.library.entity.Book;
import com.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class BookController {


    @Autowired
    BookService bookService;


    Logger logger = LoggerFactory.getLogger(BookController.class);

    @PostMapping(path = "/books")
    public @ResponseBody ResponseEntity<Book> postBook (@RequestBody Book book){
        Book newBook = bookService.newBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED) ;
    }
    @DeleteMapping(path = "/books/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Integer id){
        Optional<Book> idExists = bookService.findBook(id);
        if(idExists.isPresent()){
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(path = "/books/{id}")
    @ResponseBody
    public ResponseEntity<Book> getBookById(@PathVariable Integer id){
        Optional<Book> bookById = bookService.findBook(id);
        if(bookById.isPresent()) {
            logger.info("Book found id '{}' title '{}'", id, bookById.get().getTitle());
            return new ResponseEntity<>(bookById.get(),HttpStatus.OK);
        }else{
            logger.error("Book with id '{}' not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping(path = "/books/{id}")
    @ResponseBody
    public ResponseEntity<Book> editBook(@PathVariable("id") Integer id, @RequestBody Book book){
        Optional<Book> editedBook = bookService.editBook(id, book);
        return new ResponseEntity<>(editedBook.get(),HttpStatus.OK);
    }
    @GetMapping(path = "/books")
    @ResponseBody
    public List<Book> getAllBooks(@RequestParam(required = false) String authorSurname,
                                  @RequestParam(required = false) BigDecimal minPrice,
                                  @RequestParam(required = false) BigDecimal maxPrice,
                                  @RequestParam(required = false) String priceOrder){
        List<Book> allBooksList = bookService.findAllBooks(authorSurname, minPrice, maxPrice, priceOrder);
        return allBooksList;
    }
}
