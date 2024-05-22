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

    //add category to book
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

    //get all categories in book
    @GetMapping("/books/{id}/categories")
    public ResponseEntity<?> getAllCategoriesInBook(@PathVariable Integer id) throws Exception {
        try {
            List<Category> categories = bookService.getAllCategoriesInBook(id);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failted to retrieve categories in book");
        }
    }

    //todo get all recipes in book

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

    //delete category from book
    @DeleteMapping("/books/{id}/categories/{categoryId}")
    public void deleteCategoryFromBook(@PathVariable Integer id, @PathVariable Integer categoryId) throws Exception {
        bookService.deleteCategoryFromBook(id, categoryId);
    }

}
