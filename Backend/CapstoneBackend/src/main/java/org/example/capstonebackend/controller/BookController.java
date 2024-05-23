package org.example.capstonebackend.controller;

import org.apache.coyote.Response;
import org.example.capstonebackend.model.Book;
import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookService bookService;

//CREATE
    //add book
    @PostMapping("/books")
    public ResponseEntity<Book> addBook (@RequestBody Book book) throws Exception {
        try {
            //Trying to add book
            Book addedBook = bookService.addBook(book);
            //if successful, return OK (200) response with the added book
            return ResponseEntity.ok(addedBook);
        } catch (Exception e) {
            // If an exception occurs during adding the book, return Bad Request (400) response
            return ResponseEntity.badRequest().build();
        }
    }

    //add category to book (may be able to delete at end) todo
    @PostMapping("/users/{id}/categories/{categoryId}")
    public ResponseEntity<Book> addCategoryToBook(@PathVariable Integer id, @PathVariable Integer categoryId) throws Exception {
        try {
            Book book = bookService.addCategoryToBook(id, categoryId);
            return ResponseEntity.ok().body(book);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

//READ
    //get book
    @GetMapping("books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        try {
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //get all categories in book (may be able to delete at end) todo
    @GetMapping("/books/{id}/categories")
    public ResponseEntity<?> getAllCategoriesInBook(@PathVariable Integer id) throws Exception {
        try {
            List<Category> categories = bookService.getAllCategoriesInBook(id);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to retrieve categories in book");
        }
    }

//UPDATE
    //update book
    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody Book book) throws Exception {
        try {
            Book updatedBook = bookService.updateBook(id, book);
            return ResponseEntity.ok(updatedBook);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//DELETE
    //delete book
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }

}
