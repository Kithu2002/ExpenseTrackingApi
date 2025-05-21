package com.example.LibraryManagement.Repository;

import com.example.LibraryManagement.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
