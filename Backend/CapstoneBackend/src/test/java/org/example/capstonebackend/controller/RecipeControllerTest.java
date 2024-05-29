package org.example.capstonebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.matcher.ElementMatchers;
import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IIngredientRepository;
import org.example.capstonebackend.repository.IRecipeRepository;
import org.example.capstonebackend.repository.IUserRepository;
import org.example.capstonebackend.service.IngredientService;
import org.example.capstonebackend.service.RecipeService;
import org.example.capstonebackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.capstonebackend.components.CategoryTestUtilities.mockCategory;
import static org.example.capstonebackend.components.IngredientTestUtilities.ingredientToJson;
import static org.example.capstonebackend.components.IngredientTestUtilities.mockIngredient;
import static org.example.capstonebackend.components.RecipeTestUtilites.mockRecipe;
import static org.example.capstonebackend.components.RecipeTestUtilites.recipeToJson;
import static org.example.capstonebackend.components.UserTestUtilities.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(RecipeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RecipeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IRecipeRepository recipeRepository;

    @MockBean
    RecipeService recipeService;


//CREATE
    //create recipe
        //happy path
    @Test
    public void testAddRecipe() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String recipeToJson = objectMapper.writeValueAsString(mockRecipe);

        when(recipeService.addRecipe(any(Recipe.class))).thenReturn(mockRecipe);

        ResultActions resultActions = mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(recipeToJson));
        resultActions.andExpect(status().isOk());

        verify(recipeService).addRecipe(any(Recipe.class));
    }
        //sad path
    @Test
    public void testAddRecipe_RecipeAlreadyExists() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(1);
        recipe.setName("Marinara Sauce");
        recipe.setRecipeAuthor("Grandma Pat");

        when(recipeService.addRecipe(any(Recipe.class))).thenThrow(new Exception("Recipe already exists"));

        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipe)))
                .andExpect(status().isBadRequest());
    }

//READ
    //get recipe by name
        //happy path
    @Test
    public void testGetRecipeByName() throws Exception {

        when(recipeService.getRecipeByName(anyString())).thenReturn(mockRecipe);

        mockMvc.perform(get("/recipes/name/{name}", mockRecipe.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(1))
                .andExpect(jsonPath("$.name").value(mockRecipe.getName()));
    }
        //sad path
    @Test
    public void testGetRecipeByName_RecipeNotFound() throws Exception {
        String badName = "unicorn steak";
        when(recipeService.getRecipeByName(badName)).thenThrow(new Exception("Recipe with name " + badName + " does not exist"));

        ResultActions resultActions = mockMvc.perform(get("/recipes/name/{name}", badName)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNotFound());

        verify(recipeService).getRecipeByName(badName);
    }

    //todo get recipes by category
    @Test
    void testGetRecipesByCategory() throws Exception {
        // Arrange
        List<Recipe> mockRecipes = new ArrayList<>();
        mockRecipes.add(new Recipe());
        mockRecipes.add(new Recipe());
        mockRecipes.add(new Recipe());

        when(recipeService.getRecipesByCategory(mockCategory.getId())).thenReturn(mockRecipes);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/categories/{categoryId}/recipes", mockCategory.getId()));
        resultActions.andExpect(status().isOk());
//        List<Recipe> actualRecipes = recipeController.getRecipesByCategory(mockCategory);

        // Assert
        verify(recipeService, times(1)).getRecipesByCategory(mockCategory.getId()); // Verify method invocation
    }

    //get all recipes
        //happy path
    @Test
    public void testGetAllRecipes() throws Exception {
        List<Recipe> mockRecipes = Arrays.asList(mockRecipe, mockRecipe);

        when(recipeService.getAllRecipes()).thenReturn(mockRecipes);

        ObjectMapper objectMapper = new ObjectMapper();
        String mockRecipesJson = objectMapper.writeValueAsString(mockRecipes);

        mockMvc.perform(get("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockRecipesJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockRecipes.size()));

        verify(recipeService).getAllRecipes();
    }

//UPDATE
    //update recipe
        //happy path
@Test
public void testUpdateRecipe() throws Exception {
    int recipeId = 1;
    Recipe recipe = new Recipe();
    recipe.setRecipeId(recipeId);
    recipe.setName("Updated Recipe");
    recipe.setRecipeAuthor("UpdatedAuthor");


    Recipe updatedRecipe = new Recipe();
    updatedRecipe.setRecipeId(recipeId);
    updatedRecipe.setName("Updated Recipe");
    updatedRecipe.setRecipeAuthor("UpdatedAuthor");

    when(recipeService.updateRecipe(anyInt(), any(Recipe.class))).thenReturn(updatedRecipe);

    mockMvc.perform(put("/recipes/{recipeId}", recipeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(recipe)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.recipeId").value(recipeId))
            .andExpect(jsonPath("$.name").value("Updated Recipe"))
            .andExpect(jsonPath("$.recipeAuthor").value("UpdatedAuthor"));
}

        //sad path
    @Test
    public void TestUpdateRecipe_IdDoesNotExist() throws Exception {
        when(recipeService.updateRecipe(anyInt(), any(Recipe.class))).thenThrow(new Exception("Recipe with id " + mockRecipe.getRecipeId() + " not found"));

        mockMvc.perform(put("/recipes/{recipeId}", mockRecipe.getRecipeId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeToJson(mockRecipe)))
                .andExpect(status().isNotFound());

        verify(recipeService).updateRecipe(any(Integer.class), any(Recipe.class));
    }

//DELETE
    //delete recipe
    @Test
    public void testDeleteRecipe() throws Exception {
        int recipeId = 1;

        doNothing().when(recipeService).deleteRecipe(anyInt());

        ResultActions resultActions = mockMvc.perform(delete("/recipes/{recipeId}", recipeId)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());

        verify(recipeService).deleteRecipe(recipeId);
    }
}
