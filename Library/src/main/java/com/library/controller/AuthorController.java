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
    public @ResponseBody Author postAuthor (@RequestBody Author author){
        Author newAuthor = authorService.newAuthor(author);
        return newAuthor;
    }
    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable Integer id){
        Optional<Author> idExist = authorService.findAuthor(id);
        if(idExist.isPresent()) {
            authorService.deleteAuthor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(path = "/authors/{id}")
    @ResponseBody
    public Author getAuthorWithId(@PathVariable Integer id){
        Optional<Author> authorWithId = authorService.findAuthor(id);
        if(authorWithId.isPresent()) {
            return authorWithId.get();
        }else{
            return null;
        }
    }
    @GetMapping(path = "/authors/all")
    @ResponseBody
    public List<Author> getAllAuthors(){
        List<Author> allAuthorsList = authorService.findAllAuthors();
        return allAuthorsList;
    }
    @PutMapping(path = "/authors/{id}")
    @ResponseBody
    public Author editAuthor(@PathVariable("id") Integer id, @RequestBody Author author){
        Optional<Author> editedAuthor = authorService.editAuthor(id, author);
        return editedAuthor.get();
    }


}
