package com.example.LibraryManagement.Service;

import com.example.LibraryManagement.Entity.Book;
import com.example.LibraryManagement.Entity.Record;
import com.example.LibraryManagement.Entity.User;
import com.example.LibraryManagement.Repository.BookRepository;
import com.example.LibraryManagement.Repository.RecordRepository;
import com.example.LibraryManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.time.LocalDate;
import java.util.Date;

@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public Record issueBook(int bookId) {
        Book book =bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("Book not found"));

        if(book.getQuantity()<=0||!book.isAvailable()){
            throw new RuntimeException("Book is not available");
        }

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).orElseThrow(()->new RuntimeException("User not found"));

        Record record = new Record();

        record.setIssueDate(LocalDate.now());
        record.setBook(book);
        record.setUser(user);
        record.setDueDate(LocalDate.now().plusDays(14));
        record.setReturnDate(null);
        record.setReturned(false);

        book.setQuantity(book.getQuantity()-1);

        if(book.getQuantity()<=0){
            book.setAvailable(false);
        }

        bookRepository.save(book);
        return recordRepository.save(record);






    }

    public Record returnBook(int recordid) {
        Record record = recordRepository.findById(recordid)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        Book book = record.getBook();
        book.setQuantity(book.getQuantity() + 1);
        book.setAvailable(true);
        bookRepository.save(book);

        record.setReturned(true);
        record.setReturnDate(LocalDate.now());

        return recordRepository.save(record);
    }

}
