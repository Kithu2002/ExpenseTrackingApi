package com.example.LibraryManagement.Controller;

import com.example.LibraryManagement.Entity.Record;
import com.example.LibraryManagement.Service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;


    @PostMapping("/issuebook/{bookId}")
    public ResponseEntity<Record> issueBook(@PathVariable int bookId){
        return ResponseEntity.ok(recordService.issueBook(bookId));

    }


    @PostMapping("/returnbook/{recordid}")
    public ResponseEntity<Record> returnBook(@PathVariable int recordid) {
        return ResponseEntity.ok(recordService.returnBook(recordid));
    }







}
