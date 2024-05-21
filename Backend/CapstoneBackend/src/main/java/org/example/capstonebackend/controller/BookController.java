package org.example.capstonebackend.controller;

import org.example.capstonebackend.model.Book;
import org.example.capstonebackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @Autowired
    BookService bookService;

//CREATE
    //add book todo how do I make it so that it only allows one book to be added per user?
    @PostMapping("/posts")
    public ResponseEntity<Book> addBook (@RequestBody Book book) throws Exception {
        try {
            //Trying to add post
            Book addedBook = bookService.addBook(book);
            //if successful, return OK (200) response with the added post
            return ResponseEntity.ok(addedBook);
        } catch (Exception e) {
            // If an exception occurs during adding the post, return Bad Request (400) response
            return ResponseEntity.badRequest().build();
        }
    }
}
