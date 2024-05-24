package org.example.capstonebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.Book;
import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.repository.IBookRepository;
import org.example.capstonebackend.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.example.capstonebackend.components.BookTestUtilities.bookToJson;
import static org.example.capstonebackend.components.BookTestUtilities.mockBook;
import static org.example.capstonebackend.components.IngredientTestUtilities.ingredientToJson;
import static org.example.capstonebackend.components.IngredientTestUtilities.mockIngredient;

import static org.example.capstonebackend.components.UserTestUtilities.mockUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBookRepository bookRepository;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookController bookController;

//CREATE
    //add book
        //sad path
    @Test
    public void testAddBook() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String bookToJson = objectMapper.writeValueAsString(mockBook);

        when(bookService.addBook(any(Book.class))).thenReturn(mockBook);

        ResultActions resultActions = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookToJson));
        resultActions.andExpect(status().isOk());

        verify(bookService).addBook(any(Book.class));
    }
        //sad path
    @Test
    public void testAddBook_BookAlreadyExists() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Emily's Cook Book");

        when(bookService.addBook(any(Book.class))).thenThrow(new Exception("Book already exists"));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isBadRequest());
    }

    //add category to book
        //happy path
    @Test
    public void testAddCategoryToBook() throws Exception {
        // Arrange
        Integer bookId = 1;
        Integer categoryId = 1;

        when(bookService.addCategoryToBook(bookId, categoryId)).thenReturn(mockBook);

        // Act & Assert
        mockMvc.perform(post("/books/{id}/categories/{categoryId}", bookId, categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockBook.getId()))
                .andExpect(jsonPath("$.title").value(mockBook.getTitle()));

        verify(bookService, times(1)).addCategoryToBook(bookId, categoryId);
    }

        //sad path
    @Test
    public void testAddCategoryToBook_NotFound() throws Exception {
        // Arrange
        Integer bookId = 1;
        Integer categoryId = 1;

        when(bookService.addCategoryToBook(bookId, categoryId)).thenThrow(new Exception("Book or category not found"));

        // Act & Assert
        mockMvc.perform(post("/books/{id}/categories/{categoryId}", bookId, categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).addCategoryToBook(bookId, categoryId);
    }

//READ
    //get book
        //happy path
    @Test
    public void testGetBookById() throws Exception {
        Integer bookId = 1;

        when(bookService.getBookById(anyInt())).thenReturn(mockBook);

        mockMvc.perform(get("/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
        //sad path
    @Test
    public void testGetBookById_IdNotFound() throws Exception {
        when(bookService.getBookById(mockBook.getId())).thenThrow(new Exception("Book with id " + mockBook.getId() + " not found"));

        mockMvc.perform(get("/books/{id}", mockBook.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bookService).getBookById(mockBook.getId());
    }

    //get all categories in book
        //todo happy path
    @Test
    public void testGetAllCategoriesInBook_HappyPath() throws Exception {
        // Arrange
        Integer bookId = 1;
        List<Category> expectedCategories = Arrays.asList(new Category(1, "Soups"), new Category(2, "Dessert"));
        when(bookService.getAllCategoriesInBook(bookId)).thenReturn(expectedCategories);

        // Act & Assert
        mockMvc.perform(get("/books/{id}/categories", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) // Check if the response is an array
                .andExpect(jsonPath("$", hasSize(2))) // Check if the array has size 2
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Soups"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Dessert"));
    }

//UPDATE
    //update book
        //happy path
@Test
public void testUpdateBook() throws Exception {
    int bookId = 1;
    Book book = new Book();
    book.setId(bookId);
    book.setTitle("Updated Book");

    Book updatedBook = new Book();
    updatedBook.setId(bookId);
    updatedBook.setTitle("Updated Book");

    when(bookService.updateBook(anyInt(), any(Book.class))).thenReturn(updatedBook);

    mockMvc.perform(put("/books/{id}", bookId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(book)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(bookId))
            .andExpect(jsonPath("$.title").value("Updated Book"));
}

        //sad path
    @Test
    public void TestUpdateBook_IdDoesNotExist() throws Exception {
        when(bookService.updateBook(anyInt(), any(Book.class))).thenThrow(new Exception("Book with id " + mockBook.getId() + " not found"));

        mockMvc.perform(put("/books/{id}", mockBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookToJson(mockBook)))
                .andExpect(status().isNotFound());

        verify(bookService).updateBook(any(Integer.class), any(Book.class));
    }

//DELETE
    //delete book
        //happy path
    @Test public void testDeleteBook() throws Exception {
        int id = 1;

        doNothing().when(bookService).deleteBook(anyInt());

        ResultActions resultActions = mockMvc.perform(delete("/books/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());

        verify(bookService).deleteBook(id);
    }



}
