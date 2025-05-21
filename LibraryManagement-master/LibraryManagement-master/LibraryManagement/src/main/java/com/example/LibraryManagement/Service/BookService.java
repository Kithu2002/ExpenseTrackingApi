package com.example.LibraryManagement.Service;


import com.example.LibraryManagement.DTO.BookDTO;
import com.example.LibraryManagement.Entity.Book;
import com.example.LibraryManagement.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;



    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElseThrow(()->new RuntimeException("Book not found"));
    }

    public Book addBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setQuantity(bookDTO.getQuantity());
        book.setAvailable(true);


        return bookRepository.save(book);
    }

    public Book updateBook( int id,BookDTO bookDTO) {
        Book oldBook = bookRepository.findById(id).orElseThrow(()->new RuntimeException("Book not found"));

        oldBook.setAuthor(bookDTO.getAuthor());
        oldBook.setTitle(bookDTO.getTitle());
        oldBook.setIsbn(bookDTO.getIsbn());
        oldBook.setQuantity(bookDTO.getQuantity());
        oldBook.setAvailable(bookDTO.isAvailable());
        return bookRepository.save(oldBook);



    }

    public void deleteBook(int id) {

         


        bookRepository.deleteById(id);

    }
}
