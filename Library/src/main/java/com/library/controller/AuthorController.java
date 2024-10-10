package com.library.controller;

import com.library.entity.Author;
import com.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Author>> getAllAuthors(@RequestParam(required = false) String surname,
                                      @RequestParam(required = false) String nationality){
        List<Author> allAuthorsList = authorService.findAllAuthors(surname, nationality);
        return new ResponseEntity<>(allAuthorsList, HttpStatus.OK);
    }
    @PutMapping(path = "/authors/{id}")
    @ResponseBody
    public ResponseEntity<Author> editAuthor(@PathVariable("id") Integer id, @RequestBody Author author){
        Optional<Author> editedAuthor = authorService.editAuthor(id, author);
        if(editedAuthor.isPresent()) {
            return new ResponseEntity<>(editedAuthor.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(path = "/authors/nationality/{nationality}")
    @ResponseBody
    public ResponseEntity <List<Author>> getAuthorByNationality1(@PathVariable String nationality){
        List<Author> authorsByNationality = authorService.findAuthorByNationality1(nationality);
        return new ResponseEntity<>(authorsByNationality, HttpStatus.OK);
    }
    @GetMapping(path = "/authors/nationalitySurname/{nationality}/{surname}")
    @ResponseBody
    public ResponseEntity <List<Author>> getAuthorsByNationalityAndSurname(@PathVariable String nationality, @PathVariable String surname){
        List<Author> authorServiceAuthorByNationalityAndSurname = authorService.findAuthorByNationalityAndSurname(nationality, surname);
        return new ResponseEntity<>(authorServiceAuthorByNationalityAndSurname, HttpStatus.OK);
    }


}
