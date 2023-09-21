package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import com.library.specifications.AuthorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Author> findAllAuthors(String surname, String nationality){
        List<Author> authors;
        if(surname == null && nationality == null){
            authors = authorRepository.findAll();
        }else if(surname == null && nationality != null){
            authors = authorRepository.findByNationality(nationality);
        }else if(surname != null && nationality == null){
            authors = authorRepository.findBySurname(surname);
        }else{
            authors = authorRepository.findBySurnameAndNationality(surname, nationality);
        }
        return authors;
    }

    @Transactional //significa que en este momento va solo este metodo, por causa varias acciones(find, save..)
    public Optional<Author> editAuthor(Integer id, Author author){
        Optional<Author> find = authorRepository.findById(id);
        if(find.isPresent()) {
            Author authorBD = find.get(); //valor de Optional
            authorBD.setName(author.getName());
            authorBD.setSurname(author.getSurname());
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
    public List<Author> findAuthorByNationality1(String nationality){
        List<Author> authorsList = authorRepository.findByNationality(nationality);
        return authorsList;
    }
    public List<Author>findAuthorByNationalityAndSurname(String nationality, String surname){
        List<Author> authorsList = authorRepository.findAll(AuthorSpecification.getAuthorByNatAndSurname(nationality, surname));
        return authorsList;
    }


}
