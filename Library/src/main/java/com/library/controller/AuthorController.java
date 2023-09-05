package com.library.controller;

import com.library.entity.Author;
import com.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping(path = "/authors")
    public @ResponseBody ResponseEntity<Author> postAuthor (@RequestBody Author author){
        Author newAuthor = authorService.newAuthor(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }
    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable Integer id){
        Optional<Author> idExists = authorService.findAuthor(id);
        if(idExists.isPresent()) {
            authorService.deleteAuthor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(path = "/authors/{id}")
    @ResponseBody
    public ResponseEntity<Author> getAuthorById(@PathVariable Integer id){
        Optional<Author> authorById = authorService.findAuthor(id);
        if(authorById.isPresent()) {
            return new ResponseEntity<>(authorById.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(path = "/authors")
    @ResponseBody
    public List<Author> getAllAuthors(){
        List<Author> allAuthorsList = authorService.findAllAuthors();
        return allAuthorsList;
    }
    @PutMapping(path = "/authors/{id}")
    @ResponseBody
    public ResponseEntity<Author> editAuthor(@PathVariable("id") Integer id, @RequestBody Author author){
        Optional<Author> editedAuthor = authorService.editAuthor(id, author);
        return new ResponseEntity<>(editedAuthor.get(),HttpStatus.OK);
    }


}
