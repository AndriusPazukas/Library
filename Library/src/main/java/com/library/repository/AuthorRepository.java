package com.library.repository;

import com.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>, JpaSpecificationExecutor<Author> {

    List<Author> findByNationality(String nationality);
    //      % uz parametro reiskia ieskoti pagal pradzia, pries parametra - pagal pabaiga
    @Query("SELECT a FROM Author a WHERE a.surname LIKE %:surname%")
    List<Author> findBySurname(String surname);

    List<Author> findBySurnameAndNationality(String surname, String nationality);
}
