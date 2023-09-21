package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    //                                 kaip santykiauja                    parametras pagal ka ieskoti
    @Query("SELECT b FROM Book b JOIN Author a ON a.id = b.author.id WHERE a.surname = :authorSurname")
    List<Book> findByAuthorSurname(String authorSurname);

    //                                          kaip santykiauja ir parametras pagal ka ieskoti galima prideti daug AND arba OR
    @Query("SELECT b FROM Book b, Author a WHERE a.id = b.author.id AND a.surname = :authorSurname")
    List<Book> findByAuthorSurname2(String authorSurname);



    @Query("SELECT b FROM Book b WHERE b.price >= :minBookPrice")
    List<Book> findByPrice(BigDecimal minBookPrice);

    @Query("SELECT b FROM Book b WHERE b.price <= :maxBookPrice")
    List<Book> findByPrice2(BigDecimal maxBookPrice);
}


    //Uzduotis query pagal min kaina ir max kaina