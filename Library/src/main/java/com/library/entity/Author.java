package com.library.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String authorName;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column
    private String nationality;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    List<Book> books;


    public Author() {
    }

    public Author(String authorName, String dateOfBirth, String nationality) {
        super();
        this.authorName = authorName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;

    }

    public int getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
