package org.example.capstonebackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.example.capstonebackend.model.Book;
import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.service.BookService;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@Import(BookController.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @InjectMocks
    BookController bookController;

    private final Book mockBook = createMockBook();
    private final Category mockCategory = createMockCategory();
    private final Recipe mockRecipe = createMockRecipe();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String title = "Test CookBook";

    private static final String recipeName = "Test Recipe";
    private static final String author = "Test Author";
    private static final String description = "crunchy";

    private static Book createMockBook() {
        Book book = new Book();
        book.setId(1);
        book.setTitle(title);

        return book;
    }

    private static Category createMockCategory() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setTitle("Test Category");

        return category;
    }

    private static Recipe createMockRecipe() {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(1);
        recipe.setRecipeName(recipeName);
        recipe.setRecipeAuthor(author);
        recipe.setRecipeDescription(description);

        return recipe;
    }

    private String objectToJson (Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
