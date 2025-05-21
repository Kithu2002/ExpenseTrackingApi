package com.example.LibraryManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.management.relation.Role;
import java.util.Set;
@Entity
@Data
@Table(name = "book")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String title;
    private String author;
    private String isbn;
    private Integer quantity;
    @Column(nullable = false)
    private boolean available  = true;



}
