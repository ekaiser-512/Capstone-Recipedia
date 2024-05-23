package org.example.capstonebackend.service;

import jakarta.inject.Inject;
import org.example.capstonebackend.components.IngredientTestUtilities;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IIngredientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.capstonebackend.components.IngredientTestUtilities.createMockIngredient;
import static org.example.capstonebackend.components.IngredientTestUtilities.mockIngredient;
import static org.example.capstonebackend.components.UserTestUtilities.mockUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class IngredientServiceTest {
    @MockBean
    private IIngredientRepository ingredientRepository;

    @Autowired
    private IngredientService ingredientService;
    @Inject
    private IngredientTestUtilities ingredientTestUtilities;

//CREATE
    //add ingredient
        //happy path
    @Test
    public void testAddIngredient() throws Exception {
        // Mock ingredientRepository to return an empty Optional when checking for existing ingredient
        when(ingredientRepository.findByName(mockIngredient.getName())).thenReturn(Optional.empty());

        // Mock ingredientRepository to return the ingredient when saving
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(mockIngredient);

        // Call the method under test
        Ingredient savedIngredient = ingredientService.addIngredient(mockIngredient);

        // Assert that the returned ingredient is the same as the mocked saved ingredient
        assertEquals(mockIngredient, savedIngredient);
    }

        //sad path
    @Test
    public void testAddIngredient_AlreadyExists() throws Exception {
        when(ingredientRepository.save(any())).thenReturn(null);
        when(ingredientRepository.findByName(any())).thenReturn((Optional.of(mockIngredient)));

        assertThrows(Exception.class, () -> {
            ingredientService.addIngredient(mockIngredient);
        });

        verify(ingredientRepository, times(0)).save(mockIngredient);
    }

//READ
    //get ingredient by name
        //happy path
    @Test
    public void testGetIngredientByName() throws Exception {
        // Mock ingredientRepository to return an Optional containing the ingredient when finding by name
        when(ingredientRepository.findByName(mockIngredient.getName())).thenReturn(Optional.of(mockIngredient));

        // Call the method under test
        Ingredient result = ingredientService.getIngredientByName(mockIngredient.getName());

        // Assert that the returned ingredient is the same as the mocked ingredient
        assertEquals(mockIngredient, result);
    }
        //sad path
    @Test
    public void testGetIngredientByName_NotFound() throws Exception {
        when(ingredientRepository.findByName(mockIngredient.getName())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            ingredientService.getIngredientByName(mockIngredient.getName());
        });

        verify(ingredientRepository).findByName(mockIngredient.getName());
    }

    //get ingredient by dietary restriction
        //happy path
    @Test
    public void testGetIngredientByDiet() throws Exception {
        // Mock ingredientRepository to return an Optional containing the ingredient when finding by dietary restriction
        when(ingredientRepository.findByDietaryRestriction(mockIngredient.getDietaryRestriction())).thenReturn(Optional.of(mockIngredient));

        // Call the method under test
        Ingredient result = ingredientService.getIngredientByDiet(mockIngredient.getDietaryRestriction());

        // Assert that the returned ingredient is the same as the mocked ingredient
        assertEquals(mockIngredient, result);
    }
        //sad path
    @Test
    public void testGetIngredientByDiet_NotFound() throws Exception {
        when(ingredientRepository.findByDietaryRestriction(mockIngredient.getDietaryRestriction())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            ingredientService.getIngredientByDiet(mockIngredient.getDietaryRestriction());
        });

        verify(ingredientRepository).findByDietaryRestriction(mockIngredient.getDietaryRestriction());
    }

    //get list of ingredients that are common allergens
        //happy path
    @Test
    public void testGetIngredientsThatAreCommonAllergens_HappyPath() {
        List<Ingredient> mockIngredients = new ArrayList<>();
        mockIngredients.add(createMockIngredient());
        mockIngredients.add(createMockIngredient());

        // Mock ingredientRepository to return common allergens when searching by common allergen flag
        when(ingredientRepository.findByCommonAllergen(true)).thenReturn(mockIngredients);

        // Call the method under test
        List<Ingredient> results = ingredientService.getIngredientsThatAreCommonAllergens(true);

        // Assert that the returned ingredients are the same as the mocked common allergens
        assertEquals(mockIngredients, results);
    }

    //get list of all ingredients
        //happy path
    @Test
    public void testGetAllIngredients() {
        List<Ingredient> mockIngredients = new ArrayList<>();
        mockIngredients.add(new Ingredient());
        mockIngredients.add(new Ingredient());
        mockIngredients.add(new Ingredient());

        when(ingredientRepository.findAll()).thenReturn(mockIngredients);

        List<Ingredient> allIngredients = ingredientService.getAllIngredients();

        assertEquals(3, allIngredients.size());
        verify(ingredientRepository).findAll();
    }

//UPDATE
    //update ingredient
        //happy path
    @Test
    public void testUpdateIngredient() throws Exception {
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(mockIngredient);
        when(ingredientRepository.findById(1)).thenReturn(Optional.of(mockIngredient));

        Ingredient updatedIngredient = ingredientService.updateIngredient(1, mockIngredient);

        assertNotNull(updatedIngredient);
        assertEquals(mockIngredient, updatedIngredient);
        verify(ingredientRepository).findById(1);
    }
        //sad path
    @Test
    public void testUpdateIngredient_IngredientNotFound() throws Exception {
        when(ingredientRepository.findById(mockIngredient.getIngredientId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            ingredientService.updateIngredient(mockIngredient.getIngredientId(), mockIngredient);
        });

        assertEquals("Ingredient with id " + mockIngredient.getIngredientId() + " not found", exception.getMessage());
    }

//DELETE
    //delete ingredient
    @Test
    public void deleteIngredient() {
        doNothing().when(ingredientRepository).deleteById(anyInt());

        ingredientService.deleteIngredient(mockIngredient.getIngredientId());

        verify(ingredientRepository).deleteById(mockIngredient.getIngredientId());
    }
}
