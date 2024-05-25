package org.example.capstonebackend.service;

import org.example.capstonebackend.components.CategoryTestUtilities;
import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.capstonebackend.components.CategoryTestUtilities.mockCategory;
import static org.example.capstonebackend.components.RecipeTestUtilites.mockRecipe;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoryServiceTest {
    @MockBean
    private ICategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

//CREATE
    //create category
        //happy path
    @Test
    public void testAddCategory() throws Exception {
        when(categoryRepository.findById(mockCategory.getId())).thenReturn(Optional.empty());

        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory);

        Category savedCategory = categoryService.addCategory(mockCategory);

        assertEquals(mockCategory, savedCategory);
    }
        //sad path
    @Test
    public void testAddCategory_AlreadyExists() throws Exception {
        when(categoryRepository.save(any())).thenReturn(null);
        when(categoryRepository.findById(any())).thenReturn((Optional.of(mockCategory)));

        assertThrows(Exception.class, () -> {
            categoryService.addCategory(mockCategory);
        });

        verify(categoryRepository, times(0)).save(mockCategory);
    }

    //todo add category to book

//READ
    //get category by id
        //happy path
    @Test
    public void testGetCategoryById() throws Exception {
        when(categoryRepository.findById(mockCategory.getId())).thenReturn(Optional.of(mockCategory));

        Category result = categoryService.getCategoryById(mockCategory.getId());

        assertEquals(mockCategory, result);
    }
        //sad path
    @Test
    public void testGetCategoryById_NotFound() throws Exception {
        when(categoryRepository.findById(mockCategory.getId())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            categoryService.getCategoryById(mockCategory.getId());
        });

        verify(categoryRepository).findById(mockCategory.getId());
    }

    //get all categories
    @Test
    public void testGetAllCategories() {
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(new Category());
        mockCategories.add(new Category());
        mockCategories.add(new Category());

        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<Category> allCategories = categoryService.getAllCategories();

        assertEquals(3, allCategories.size());
        verify(categoryRepository).findAll();
    }

//UPDATE
    //update recipe
        //happy path
    @Test
    public void testUpdateCategory() throws Exception {
        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(mockCategory));

        Category updatedCategory = categoryService.updateCategory(1, mockCategory);

        assertNotNull(updatedCategory);
        assertEquals(mockCategory, updatedCategory);
        verify(categoryRepository).findById(1);
    }
        //sad path
    @Test
    public void testUpdateCategory_CategoryNotFound() throws Exception {
        when(categoryRepository.findById(mockCategory.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            categoryService.updateCategory(mockCategory.getId(), mockCategory);
        });

        assertEquals("Category with id " + mockCategory.getId() + " not found", exception.getMessage());
    }



//DELETE
    //delete category
    @Test
    public void deleteCategory() {
        doNothing().when(categoryRepository).deleteById(anyInt());

        categoryService.deleteCategory(mockCategory.getId());

        verify(categoryRepository).deleteById(mockCategory.getId());
    }

}
