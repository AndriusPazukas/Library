package com.library.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "book_name")
    private String name;
    @ManyToOne
    @JoinColumn
    private Author author;
    @Column(name = "price")
    private double price;
    @Column(name = "quantity")
    private int quantity;

    public Book() {
    }

    public Book(String name, Author author, double price, int quantity){
        super();
        this.name = name;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
