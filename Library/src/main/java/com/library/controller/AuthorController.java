package com.library.controller;

import com.library.entity.Author;
import com.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@Component //para que aplicacion functiona he anotado controller con component e etc
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping(path = "/POST")
    public @ResponseBody Author postAuthor (@RequestParam String authorName,
                                            String dateOfBirth, String nationality){
        Author newAuthor = authorService.newAuthor(new Author());
        return newAuthor;
    }
    @DeleteMapping(path = "/DELETE/{id}")
    public void deleteAuthor(@PathVariable Integer id){
        authorService.deleteAuthor(id);
    }
    @GetMapping(path = "/GET/{id}")
    public Optional getAuthorWithId(@PathVariable Integer id){
        Optional authorWithId = authorService.findAuthor(id);
        if(authorWithId.isPresent()) {
            return Optional.of(authorWithId);
        }else{
            return Optional.empty();
        }
    }
    @PutMapping(path = "/PUT/{id}")
    public Author editAuthor(@PathVariable Integer id, Author author){
        Author editedAuthor = authorService.editAuthor();//Ni idea que tengo que meter aqui
        return editedAuthor;
    }


}
