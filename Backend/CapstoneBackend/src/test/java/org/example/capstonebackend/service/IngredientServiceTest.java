package org.example.capstonebackend.service;

import jakarta.inject.Inject;
import org.example.capstonebackend.components.IngredientTestUtilities;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.repository.IIngredientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.example.capstonebackend.components.IngredientTestUtilities.mockIngredient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        when(ingredientRepository.save(eq(mockIngredient))).thenReturn(mockIngredient);

        Ingredient result = ingredientService.addIngredient(mockIngredient);

        assertEquals(mockIngredient, result);
        verify(ingredientRepository).save(mockIngredient);
    }

        //sad path
    @Test
    public void testAddIngredient_AlreadyExists() throws Exception {
        when(ingredientRepository.existsById(any())).thenReturn(true);
        when(ingredientRepository.save(any())).thenReturn(null);
        when(ingredientRepository.findById(any())).thenReturn((Optional.of(mockIngredient)));

        assertThrows(Exception.class, () -> {
            ingredientService.addIngredient(mockIngredient);
        });

        verify(ingredientRepository, times(0)).save(mockIngredient);
    }
}
