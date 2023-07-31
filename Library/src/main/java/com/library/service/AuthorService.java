package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import com.sun.source.doctree.AuthorTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping
    public @ResponseBody Optional<Author> newAuthor(@RequestParam String name){
        Optional<Author> author = authorRepository.findById(1);
        //Optional<Author> author = authorRepository.save();
        return author;
        // Why i can not find int id from class Author
        // Why posting i have to findById author in authorRepository, it seems more that i have to find object by get
    }
    @GetMapping
    public @ResponseBody Optional<Author> findAuthor(@RequestParam String name){
        Optional<Author> author = authorRepository.findById(1);
        return author;
    }
    //What does annotations @ResponseBody and @RequestParam mean and do
    //I do all the methods with authorRepository.findById because i cant find better method there, that would fit better
    @PatchMapping
    public @ResponseBody Optional<Author> editAuthor(@RequestParam String name){
        Optional<Author> author = authorRepository.findById(1);
        return author;

    }


}
