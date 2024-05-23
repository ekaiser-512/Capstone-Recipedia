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
import java.util.Arrays;
import java.util.Optional;

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

    private final Book mockBook = createMockBook();
    private final Category mockCategory = createMockCategory();
    private static final Category addedCategory = mockCategory(1);
    private static final Category deletedCategory = mockCategory(2);
    private static final Recipe addedRecipe = mockRecipe(1);
    private static final Recipe deletedRecipe = mockRecipe(2);

    private static Book createMockBook() {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");
        book.setCategories(Arrays.asList(addedCategory));
        book.setRecipes(Arrays.asList(addedRecipe));
        book.setCategories(Arrays.asList(deletedCategory));
        book.setRecipes(Arrays.asList(deletedRecipe));

        return book;
    }

    private static Category createMockCategory() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setTitle("Test Category");

        return category;
    }

    private static Category mockCategory(Integer categoryId) {
        Category category = new Category();
        category.setCategoryId(1);
        category.setTitle("Test Category");

        return category;
    }

    private static Recipe mockRecipe(Integer recipeId) {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(1);
        recipe.setName("Test Recipe");
        recipe.setRecipeAuthor("Test Author");
        recipe.setRecipeDescription("Its crunchy");

        return recipe;
    }

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
    public void testAddCategoryToBook() throws Exception {
        when(bookRepository.findById(any())).thenReturn(Optional.of(mockBook));
        when(categoryRepository.findById(any())).thenReturn(Optional.of(mockCategory));

        Book result = bookService.addCategoryToBook(mockBook.getId(), mockCategory.getCategoryId());

        assertNotNull(result);
        assertTrue(result.getCategories().contains(mockCategory));
        verify(bookRepository).findById(mockBook.getId());
        verify(categoryRepository).findById(mockCategory.getCategoryId());
        verify(bookRepository).save(mockBook);
        verify(categoryRepository).save(mockCategory);
    }

}

