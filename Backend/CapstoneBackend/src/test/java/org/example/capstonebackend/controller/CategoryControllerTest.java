package org.example.capstonebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.repository.ICategoryRepository;
import org.example.capstonebackend.repository.IRecipeRepository;
import org.example.capstonebackend.service.CategoryService;
import org.example.capstonebackend.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.example.capstonebackend.components.BookTestUtilities.mockBook;
import static org.example.capstonebackend.components.CategoryTestUtilities.categoryToJson;
import static org.example.capstonebackend.components.CategoryTestUtilities.mockCategory;
import static org.example.capstonebackend.components.IngredientTestUtilities.ingredientToJson;
import static org.example.capstonebackend.components.IngredientTestUtilities.mockIngredient;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

@Autowired
private MockMvc mockMvc;

@MockBean
private ICategoryRepository categoryRepository;

@MockBean
private CategoryService categoryService;

@MockBean
private IRecipeRepository recipeRepository;

@MockBean
private RecipeService recipeService;

//CREATE
    //create category
        //happy path
    @Test
    public void testAddCategory() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String categoryToJson = objectMapper.writeValueAsString(mockCategory);

        when(categoryService.addCategory(any(Category.class))).thenReturn(mockCategory);

        ResultActions resultActions = mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryToJson));
        resultActions.andExpect(status().isOk());

        verify(categoryService).addCategory(any(Category.class));
    }

        //sad path
    @Test
    public void testAddCategory_AlreadyExists() throws Exception {
        Category category = new Category();
        category.setCategoryId(1);
        category.setTitle("Dessert");

        when(categoryService.addCategory(any(Category.class))).thenThrow(new Exception("Category already exists"));

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(category)))
                .andExpect(status().isBadRequest());
    }

    // todo add recipe to category

//READ
    //get category by id
        //happy path
    @Test
    public void testGetBookById() throws Exception {
        Integer categoryId = 1;

        when(categoryService.getCategoryById(anyInt())).thenReturn(mockCategory);

        mockMvc.perform(get("/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(1));
    }
        //sad path
    @Test
    public void testGetCategoryById_IdNotFound() throws Exception {
        when(categoryService.getCategoryById(mockCategory.getCategoryId())).thenThrow(new Exception("Category with id " + mockCategory.getCategoryId() + " not found"));

        mockMvc.perform(get("/categories/{id}", mockCategory.getCategoryId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(categoryService).getCategoryById(mockCategory.getCategoryId());
    }


    //get all categories
        //happy path
    @Test
    public void testGetAllCategories() throws Exception {
        List<Category> mockCategories = Arrays.asList(mockCategory, mockCategory);

        when(categoryService.getAllCategories()).thenReturn(mockCategories);

        ObjectMapper objectMapper = new ObjectMapper();
        String mockIngredientsJson = objectMapper.writeValueAsString(mockCategories);

        mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockIngredientsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockCategories.size()));

        verify(categoryService).getAllCategories();
    }

//UPDATE
    //update category
        //happy path
@Test
public void testUpdateCategory() throws Exception {
    int categoryId = 1;
    Category category = new Category();
    category.setCategoryId(categoryId);
    category.setTitle("Updated Category");

    Category updatedCategory = new Category();
    updatedCategory.setCategoryId(categoryId);
    updatedCategory.setTitle("Updated Category");

    when(categoryService.updateCategory(anyInt(), any(Category.class))).thenReturn(updatedCategory);

    mockMvc.perform(put("/categories/{categoryId}", categoryId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(category)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categoryId").value(categoryId))
            .andExpect(jsonPath("$.title").value("Updated Category"));
}

        //sad path
    @Test
    public void TestUpdateCategory_IdDoesNotExist() throws Exception {
        when(categoryService.updateCategory(anyInt(), any(Category.class))).thenThrow(new Exception("Category with id " + mockCategory.getCategoryId() + " not found"));

        mockMvc.perform(put("/categories/{categoryId}", mockCategory.getCategoryId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryToJson(mockCategory)))
                .andExpect(status().isNotFound());

        verify(categoryService).updateCategory(any(Integer.class), any(Category.class));
    }

//DELETE
    //delete category
    @Test public void testDeleteCategory() throws Exception {
        int categoryId = 1;

        doNothing().when(categoryService).deleteCategory(anyInt());

        ResultActions resultActions = mockMvc.perform(delete("/categories/{categoryId}", categoryId)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());

        verify(categoryService).deleteCategory(categoryId);
    }


}
