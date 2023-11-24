package com.library.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column
    private String surname;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column
    private String nationality;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @JsonManagedReference//para evitar infinidad
    List<Book> books;


    public Author() {
    }

    public Author(String name,String surname, String dateOfBirth, String nationality) {
        super();
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id= id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
