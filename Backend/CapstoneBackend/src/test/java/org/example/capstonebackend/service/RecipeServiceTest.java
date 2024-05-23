package org.example.capstonebackend.service;

import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.repository.IRecipeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.capstonebackend.components.IngredientTestUtilities.mockIngredient;
import static org.example.capstonebackend.components.RecipeTestUtilites.mockRecipe;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RecipeServiceTest {
    @MockBean
    private IRecipeRepository recipeRepository;

    @Autowired
    private RecipeService recipeService;

//CREATE
    //add recipe
        //happy path
    @Test
    public void testAddRecipe() throws Exception {
        when(recipeRepository.findByName(mockRecipe.getName())).thenReturn(Optional.empty());

        when(recipeRepository.save(any(Recipe.class))).thenReturn(mockRecipe);

        Recipe savedRecipe = recipeService.addRecipe(mockRecipe);

        assertEquals(mockRecipe, savedRecipe);
    }
        //sad path
    @Test
    public void testAddRecipe_AlreadyExists() throws Exception {
        when(recipeRepository.save(any())).thenReturn(null);
        when(recipeRepository.findByName(any())).thenReturn((Optional.of(mockRecipe)));

        assertThrows(Exception.class, () -> {
            recipeService.addRecipe(mockRecipe);
        });

        verify(recipeRepository, times(0)).save(mockRecipe);
    }

//READ
    //get recipe by name
        //happy path
    @Test
    public void testGetRecipeByName() throws Exception {
        when(recipeRepository.findByName(mockRecipe.getName())).thenReturn(Optional.of(mockRecipe));

        Recipe result = recipeService.getRecipeByName(mockRecipe.getName());

        assertEquals(mockRecipe, result);
    }
        //sad path
    @Test
    public void testGetRecipeByName_NotFound() throws Exception {
        when(recipeRepository.findByName(mockRecipe.getName())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            recipeService.getRecipeByName(mockRecipe.getName());
        });

        verify(recipeRepository).findByName(mockRecipe.getName());
    }

    // todo get all recipes in category
        //happy path

    //get all recipes
        //happy path
    @Test
    public void testGetAllRecipe() {
        List<Recipe> mockRecipes = new ArrayList<>();
        mockRecipes.add(new Recipe());
        mockRecipes.add(new Recipe());
        mockRecipes.add(new Recipe());

        when(recipeRepository.findAll()).thenReturn(mockRecipes);

        List<Recipe> allRecipes = recipeService.getAllRecipes();

        assertEquals(3, allRecipes.size());
        verify(recipeRepository).findAll();
    }

//UPDATE
    //update recipe
        //happy path
    @Test
    public void testUpdateRecipe() throws Exception {
        when(recipeRepository.save(any(Recipe.class))).thenReturn(mockRecipe);
        when(recipeRepository.findById(1)).thenReturn(Optional.of(mockRecipe));

        Recipe updatedRecipe = recipeService.updateRecipe(1, mockRecipe);

        assertNotNull(updatedRecipe);
        assertEquals(mockRecipe, updatedRecipe);
        verify(recipeRepository).findById(1);
    }
        //sad path
    @Test
    public void testUpdateRecipe_RecipeNotFound() throws Exception {
        when(recipeRepository.findById(mockRecipe.getRecipeId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            recipeService.updateRecipe(mockRecipe.getRecipeId(), mockRecipe);
        });

        assertEquals("Recipe with id " + mockRecipe.getRecipeId() + " not found", exception.getMessage());
    }

//DELETE
    //delete recipe
    @Test
    public void deleteRecipe() {
        doNothing().when(recipeRepository).deleteById(anyInt());

        recipeService.deleteRecipe(mockRecipe.getRecipeId());

        verify(recipeRepository).deleteById(mockRecipe.getRecipeId());
    }

}
