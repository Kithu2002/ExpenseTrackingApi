package com.example.LibraryManagement.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private LocalDate dueDate;
    private boolean isReturned;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User user;


    @ManyToOne(cascade = CascadeType.ALL)  // Cascade delete records when a book is deleted
    @JoinColumn(name = "book_id")
    private Book book;
}
