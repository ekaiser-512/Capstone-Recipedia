package org.example.capstonebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.matcher.ElementMatchers;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.example.capstonebackend.components.IngredientTestUtilities.ingredientToJson;
import static org.example.capstonebackend.components.IngredientTestUtilities.mockIngredient;
import static org.example.capstonebackend.components.RecipeTestUtilites.mockRecipe;
import static org.example.capstonebackend.components.RecipeTestUtilites.recipeToJson;
import static org.example.capstonebackend.components.UserTestUtilities.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(RecipeController.class)
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
        recipe.setRecipeName("Marinara Sauce");
        recipe.setRecipeAuthor("Grandma Pat");

        when(recipeService.addRecipe(any(Recipe.class))).thenThrow(new Exception("Recipe already exists"));

        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipe)))
                .andExpect(status().isBadRequest());
    }

    //add ingredient to recipe
        //happy path
    @Test
    public void testAddIngredientToRecipe() throws Exception {
        // Mock the recipeService to return the updated recipe
        when(recipeService.addIngredientToRecipe(anyInt(), anyInt())).thenReturn(mockRecipe);

        // Perform the POST request and check the response
        mockMvc.perform(post("/recipes/{recipeId}/ingredients/{ingredientId}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(mockRecipe.getRecipeId()))
                .andExpect(jsonPath("$.recipeName").value(mockRecipe.getRecipeName()));
        // Add other necessary JSON path checks for the recipe fields

        // Verify that the service method was called with the correct parameters
        verify(recipeService).addIngredientToRecipe(1, 1);
    }
        //sad path
    @Test
    public void testAddIngredientToRecipe_RecipeOrIngredientNotFound() throws Exception {
        // Mock the recipeService to throw an exception
        when(recipeService.addIngredientToRecipe(anyInt(), anyInt())).thenThrow(new Exception("Recipe or ingredient not found"));

        // Perform the POST request and check the response
        mockMvc.perform(post("/recipes/{recipeId}/ingredients/{ingredientId}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Verify that the service method was called with the correct parameters
        verify(recipeService).addIngredientToRecipe(1, 1);
    }


//READ
    //get recipe by name
        //happy path
    @Test
    public void testGetRecipeByName() throws Exception {

        when(recipeService.getRecipeByName(anyString())).thenReturn(mockRecipe);

        mockMvc.perform(get("/recipes/recipeName/{recipeName}", mockRecipe.getRecipeName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(1))
                .andExpect(jsonPath("$.recipeName").value(mockRecipe.getRecipeName()));
    }
        //sad path
    @Test
    public void testGetRecipeByName_RecipeNotFound() throws Exception {
        String badName = "unicorn steak";
        when(recipeService.getRecipeByName(badName)).thenThrow(new Exception("Recipe with name " + badName + " does not exist"));

        ResultActions resultActions = mockMvc.perform(get("/recipes/recipeName/{recipeName}", badName)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNotFound());

        verify(recipeService).getRecipeByName(badName);
    }

    //get recipes by author
        //happy path
    @Test
    public void testGetRecipeByAuthor() throws Exception {

        when(recipeService.getRecipeByAuthor(anyString())).thenReturn(mockRecipe);

        mockMvc.perform(get("/recipes/recipeAuthor/{recipeAuthor}", mockRecipe.getRecipeAuthor())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(1))
                .andExpect(jsonPath("$.recipeAuthor").value(mockRecipe.getRecipeAuthor()));
    }
        //sad path
    @Test
    public void testGetRecipeByAuthor_AuthorNotFound() throws Exception {
        String badAuthor = "Harry Potter";
        when(recipeService.getRecipeByAuthor(badAuthor)).thenThrow(new Exception("Recipe with author " + badAuthor + " does not exist"));

        ResultActions resultActions = mockMvc.perform(get("/recipes/recipeAuthor/{recipeAuthor}", badAuthor)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNotFound());

        verify(recipeService).getRecipeByAuthor(badAuthor);
    }

    //todo get recipes in category
        //todo happy path
        //todo sad path

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
    recipe.setRecipeName("Updated Recipe");
    recipe.setRecipeAuthor("UpdatedAuthor");


    Recipe updatedRecipe = new Recipe();
    updatedRecipe.setRecipeId(recipeId);
    updatedRecipe.setRecipeName("Updated Ingredient");
    updatedRecipe.setRecipeAuthor("UpdatedAuthor");

    when(recipeService.updateRecipe(anyInt(), any(Recipe.class))).thenReturn(updatedRecipe);

    mockMvc.perform(put("/recipes/{recipeId}", recipeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(recipe)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.recipeId").value(recipeId))
            .andExpect(jsonPath("$.recipeName").value("Updated Recipe"))
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
        int id = 1;

        doNothing().when(recipeService).deleteRecipe(anyInt());

        ResultActions resultActions = mockMvc.perform(delete("/recipes/{recipeId}", id)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());

        verify(recipeService).deleteRecipe(id);
    }
}
