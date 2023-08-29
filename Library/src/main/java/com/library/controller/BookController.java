package com.library.controller;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.service.AuthorService;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class BookController {


    @Autowired
    BookService bookService;

    @PostMapping(path = "/books")
    public @ResponseBody Book postBook (@RequestBody Book book){
        Book newBook = bookService.newBook(book);
        return newBook;
    }
    @DeleteMapping(path = "/books/{id}")
    public void deleteBook(@PathVariable Integer id){
        bookService.deleteBook(id);
    }
    @GetMapping(path = "/books/{id}")
    @ResponseBody
    public Book getBookWithId(@PathVariable Integer id){
        Optional<Book> bookWithId = bookService.findBook(id);
        if(bookWithId.isPresent()) {
            return bookWithId.get();
        }else{
            return null;
        }
    }
    @PutMapping(path = "/books/{id}")
    @ResponseBody
    public Book editBook(@PathVariable("id") Integer id, @RequestBody Book book){
        Optional<Book> editedBook = bookService.editBook(id, book);
        return editedBook.get();
    }
}
