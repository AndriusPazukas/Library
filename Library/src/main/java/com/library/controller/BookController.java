package com.library.controller;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.service.AuthorService;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class BookController {


    @Autowired
    BookService bookService;

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
            return new ResponseEntity<>(bookById.get(),HttpStatus.OK);
        }else{
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
    public List<Book> getAllBooks(){
        List<Book> allBooksList = bookService.findAllBooks();
        return allBooksList;
    }
}
