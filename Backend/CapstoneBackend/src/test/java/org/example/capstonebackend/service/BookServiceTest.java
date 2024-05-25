package org.example.capstonebackend.service;

import org.example.capstonebackend.model.Book;
import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.repository.IBookRepository;
import org.example.capstonebackend.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;
import java.util.*;

import static org.example.capstonebackend.components.BookTestUtilities.mockBook;
import static org.example.capstonebackend.components.CategoryTestUtilities.mockCategory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {
    @MockBean
    IBookRepository bookRepository;

    @MockBean
    ICategoryRepository categoryRepository;

    @Autowired
    BookService bookService;

//CREATE
    //create book
        //happy path
    @Test
    public void testAddBook() throws Exception {
        when(bookRepository.save(eq(mockBook))).thenReturn(mockBook);

        Book result = bookService.addBook(mockBook);

        assertEquals(mockBook, result);
        verify(bookRepository).save(mockBook);
    }

        //sad path
    @Test
    public void testAddBook_BooksExists() throws Exception {
        when(bookRepository.existsById(any())).thenReturn(true);
        when(bookRepository.save(any())).thenReturn(null);
        when(bookRepository.findByTitle(any())).thenReturn(Optional.of(mockBook));

        assertThrows(Exception.class, () -> {
            bookService.addBook(mockBook);
        });

        verify(bookRepository,times(0)).save(mockBook);
    }

    //add category to book
        //happy path
    @Test
    public void testAddCategoryToBook_HappyPath() throws Exception {
        // Arrange
        Integer bookId = 1;
        Integer categoryId = 1;

        Book book = new Book();
        book.setId(bookId);
        book.setCategories(new ArrayList<>());

        Category category = new Category();
        category.setId(categoryId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        Book updatedBook = bookService.addCategoryToBook(bookId, categoryId);

        // Assert
        assertEquals(1, updatedBook.getCategories().size());
        assertEquals(category, updatedBook.getCategories().iterator().next());

        verify(bookRepository, times(1)).findById(bookId);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(bookRepository, times(1)).save(book);
        verify(categoryRepository, times(1)).save(category);
    }

        //sad path
    @Test
    public void testAddCategoryToBook_CategoryNotFound() {
        // Arrange
        Integer bookId = 1;
        Integer categoryId = 1;

        Book book = new Book();
        book.setId(bookId);
        book.setCategories(new ArrayList<>());

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            bookService.addCategoryToBook(bookId, categoryId);
        });

        assertEquals("Category with id " + categoryId + " not found", exception.getMessage());

        verify(bookRepository, times(1)).findById(bookId);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(bookRepository, times(0)).save(any(Book.class));
        verify(categoryRepository, times(0)).save(any(Category.class));
    }

//READ
    //get book by id
        //happy path
    @Test
    public void testGetBookById() throws Exception {
        // Arrange
        Integer bookId = 1;
        Book book = new Book();
        book.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        Book foundBook = bookService.getBookById(bookId);

        // Assert
        assertEquals(book, foundBook);
        verify(bookRepository, times(1)).findById(bookId);
    }
        //sad path
    @Test
    public void testGetBookById_NotFound() {
        // Arrange
        Integer bookId = 1;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            bookService.getBookById(bookId);
        });

        assertEquals("Book with id " + bookId + " not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(bookId);
    }

    //get all categories in book
        //happy path
    @Test
    public void testGetAllCategoriesInBook() throws Exception {
        // Arrange
        Integer bookId = 1;
        Book book = new Book();
        book.setId(bookId);

        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setId(1);
        categories.add(category1);

        Category category2 = new Category();
        category2.setId(2);
        categories.add(category2);

        book.setCategories(categories);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        List<Category> foundCategories = bookService.getAllCategoriesInBook(bookId);

        // Assert
        assertEquals(categories, foundCategories);
        verify(bookRepository, times(1)).findById(bookId);
    }
        //sad path
    @Test
    public void testGetAllCategoriesInBook_IdNotFound() {
        // Arrange
        Integer bookId = 1;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            bookService.getAllCategoriesInBook(bookId);
        });

        assertEquals("Book with id " + bookId + " not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(bookId);
    }

//UPDATE
    //update book
        //happy patth
    @Test
    public void testUpdateBook() throws Exception {
        // Arrange
        Integer bookId = 1;
        Book oldBook = new Book();
        oldBook.setId(bookId);
        oldBook.setTitle("Old Title");

        Book newBook = new Book();
        newBook.setId(bookId);
        newBook.setTitle("New Title");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(oldBook));
        when(bookRepository.save(any(Book.class))).thenReturn(oldBook);

        // Act
        Book updatedBook = bookService.updateBook(bookId, newBook);

        // Assert
        assertEquals(newBook.getTitle(), updatedBook.getTitle());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(oldBook);
    }
        //sad path
    @Test
    public void testUpdateBook_IdNotFound() {
        // Arrange
        Integer bookId = 1;
        Book newBook = new Book();
        newBook.setId(bookId);
        newBook.setTitle("New Title");

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            bookService.updateBook(bookId, newBook);
        });

        assertEquals("Book with id " + bookId + " not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(0)).save(any(Book.class));
    }

//DELETE
    //delete book
@Test
public void testDeleteBook() {
    // Arrange
    Integer bookId = 1;

    // Act
    bookService.deleteBook(bookId);

    // Assert
    verify(bookRepository, times(1)).deleteById(bookId);
}
}

