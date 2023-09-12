package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book newBook(Book book){
        Book saved = bookRepository.save(book);
        return saved;
    }

    public Optional<Book> findBook(Integer id){
        Optional<Book> author = bookRepository.findById(id);
        return author;
    }

    @Transactional //significa que en este momento va solo este metodo, por causa varias acciones(find, save..)
    public Optional<Book> editBook(Integer id, Book book){
        Optional<Book> find = bookRepository.findById(id);
        if(find.isPresent()) {
            Book bookBD = find.get(); //valor de Optional
            bookBD.setAuthor(book.getAuthor());
            bookBD.setName(book.getName());
            bookBD.setPrice(book.getPrice());
            bookBD.setQuantity(book.getQuantity());
            Book changed = bookRepository.save(bookBD);
            return Optional.of(changed);
        }//uso de optional
        else{
            return Optional.empty();
        }

    }
    public void deleteBook(Integer id){
        Optional<Book> find = bookRepository.findById(id);
        if(find.isPresent()){
            bookRepository.deleteById(id);
        }
    }
    public List<Book> findAllBooks(){
        List<Book> allElements = bookRepository.findAll();
        return allElements;
    }


}
