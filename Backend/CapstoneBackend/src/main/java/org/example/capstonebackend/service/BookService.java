package org.example.capstonebackend.service;

import org.example.capstonebackend.model.Book;
import org.example.capstonebackend.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final IBookRepository bookRepository;

    @Autowired
    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

//CREATE (todo could this be updated to find by userId "book by user Id alreday exists")
    public Book addBook(Book book) throws Exception {
        // Check if a post with the same title already exists
       Optional <Object> bookExists = Optional.of(bookRepository.findById(book.getId()));

        // If a post with the same title exists, throw an exception
        if(bookExists.isPresent()) {
            throw new Exception("book with Id " + book.getId() + " already exists");
        }
        // If no post with the same title exists, save the new post and return it
        return bookRepository.save(book);
    }

//READ
    //get book by id
    public Book 
}
