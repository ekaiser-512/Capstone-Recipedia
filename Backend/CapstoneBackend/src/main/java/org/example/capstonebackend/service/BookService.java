package org.example.capstonebackend.service;

import org.example.capstonebackend.model.Book;
import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.repository.IBookRepository;
import org.example.capstonebackend.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final IBookRepository bookRepository;
    private final ICategoryRepository categoryRepository;

    @Autowired
    public BookService(IBookRepository bookRepository, ICategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

//CREATE

    //add book
    public Book addBook(Book book) throws Exception {
        // Check if a book with the same title already exists
      Optional<Book> bookExists = bookRepository.findByTitle(book.getTitle());

        // If a book with the same title exists, throw an exception
        if(bookExists.isPresent()) {
            throw new Exception("book with Title " + book.getTitle() + " already exists");
        }
        // If no post with the same title exists, save the new post and return it
        return bookRepository.save(book);
    }

    //add category to book (may be able to delete at end) todo
    public Book addCategoryToBook(Integer id, Integer categoryId) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("Book with id " + id + " not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new Exception("Category with id " + categoryId  + " not found"));

        book.getCategories().add(category);

        categoryRepository.save(category);
        bookRepository.save(book);

        return book;
    }

//READ
    //get book by id
    public Book getBookById(Integer id) throws Exception {
        return bookRepository.findById(id)
                .orElseThrow(() -> new Exception("Book with id " + id + " not found"));
    }

    //get all categories in book (may be able to delete at end) todo
    public List<Category> getAllCategoriesInBook(Integer id) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("Book with id " + id + " not found"));
        return book.getCategories();
    }

//UPDATE
    //update book
    public Book updateBook(int id, Book book) throws Exception {
        Book oldBook = bookRepository.findById(id).orElse(null);

        if(oldBook == null) {
            throw new Exception("Book with id " + id + " not found");
        }
        oldBook.setId(book.getId());
        oldBook.setTitle(book.getTitle());

        return bookRepository.save(oldBook);
    }

//DELETE
    //delete book
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }





}
