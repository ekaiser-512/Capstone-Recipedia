package org.example.capstonebackend.controller;

import org.apache.coyote.Response;
import org.example.capstonebackend.DTO.CommonResponseDTO;
import org.example.capstonebackend.DTO.CreateCategoryForBookDTO;
import org.example.capstonebackend.model.Book;
import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
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
    @PostMapping("/books/{id}/categories/{categoryId}")
    public ResponseEntity<Book> addCategoryToBook(@PathVariable Integer id, @PathVariable Integer categoryId) {
        try {
            Book book = bookService.addCategoryToBook(id, categoryId);
            return ResponseEntity.ok().body(book);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/books/{id}/categories")
    public ResponseEntity<?> createCategoryForBook(@PathVariable Integer id, @RequestBody CreateCategoryForBookDTO createCategoryForBookDTO) {
        try {
            Book book = bookService.createCategoryForBook(id, createCategoryForBookDTO.getTitle());
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(false).data(book.getCategories()).status(HttpStatus.OK).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(true).message("failed to retrieve category").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(false).data(categories).status(HttpStatus.OK).build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            CommonResponseDTO response = CommonResponseDTO.builder().hasError(true).message("failed to retrieve categories in book").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
