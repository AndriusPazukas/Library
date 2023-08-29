package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author newAuthor( Author author){
        Author saved = authorRepository.save(author);
        return saved;
    }

    public Optional<Author> findAuthor(Integer id){
        Optional<Author> author = authorRepository.findById(id);
        return author;
    }
    public List<Author> findAllAuthors(){
        Iterable<Author> allElements = authorRepository.findAll();
        List<Author> allAuthors = new ArrayList<>();
        for(Author author: allElements){
            allAuthors.add(author);
        }
        return allAuthors;
    }

    @Transactional //significa que en este momento va solo este metodo, por causa varias acciones(find, save..)
    public Optional<Author> editAuthor(Integer id, Author author){
        Optional<Author> find = authorRepository.findById(id);
        if(find.isPresent()) {
            Author authorBD = find.get(); //valor de Optional
            authorBD.setAuthorName(author.getAuthorName());
            authorBD.setDateOfBirth(author.getDateOfBirth());
            authorBD.setNationality(author.getNationality());
            Author changed = authorRepository.save(authorBD);
            return Optional.of(changed);
        }//uso de optional
        else{
            return Optional.empty();
        }

    }
    public void deleteAuthor(Integer id){
        Optional<Author> find = authorRepository.findById(id);
        if(find.isPresent()){
            authorRepository.deleteById(id);
        }
    }


}
