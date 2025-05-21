package com.example.LibraryManagement.Repository;
import com.example.LibraryManagement.Entity.Record;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    void deleteByBookId(int bookId);
}
