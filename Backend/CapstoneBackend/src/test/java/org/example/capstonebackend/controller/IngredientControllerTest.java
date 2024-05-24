package org.example.capstonebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IIngredientRepository;
import org.example.capstonebackend.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.example.capstonebackend.components.IngredientTestUtilities.ingredientToJson;
import static org.example.capstonebackend.components.IngredientTestUtilities.mockIngredient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IIngredientRepository ingredientRepository;

    @MockBean
    private IngredientService ingredientService;

//CREATE

    //add ingredient
        //happy path
    @Test
    public void testAddIngredient() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String ingredientToJson = objectMapper.writeValueAsString(mockIngredient);

        when(ingredientService.addIngredient(any(Ingredient.class))).thenReturn(mockIngredient);

        ResultActions resultActions = mockMvc.perform(post("/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ingredientToJson));
        resultActions.andExpect(status().isOk());

        verify(ingredientService).addIngredient(any(Ingredient.class));
    }

        //sad path
    @Test
    public void testAddIngredient_IngredientAlreadyExists() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(1);
        ingredient.setName("Salt");

        when(ingredientService.addIngredient(any(Ingredient.class))).thenThrow(new Exception("Ingredient already exists"));

        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ingredient)))
                .andExpect(status().isBadRequest());
    }

//READ
    //get ingredient by name
        //happy path
    @Test
    public void testGetIngredientByName() throws Exception {
        String ingredientName = "Salt";
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(1);
        ingredient.setName(ingredientName);

        when(ingredientService.getIngredientByName(anyString())).thenReturn(ingredient);

        mockMvc.perform(get("/ingredients/ingredientName/{ingredientName}", ingredientName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientId").value(1))
                .andExpect(jsonPath("$.name").value(ingredientName));
    }
        //sad path
    @Test
    public void testGetIngredientByName_IngredientNotFound() throws Exception {
        String badName = "unicorn blood";
        when(ingredientService.getIngredientByName(badName)).thenThrow(new Exception("Ingredient with name " + badName + " does not exist"));

        ResultActions resultActions = mockMvc.perform(get("/ingredients/ingredientName/{ingredientName}", badName)
                .contentType(MediaType.APPLICATION_JSON));
            resultActions.andExpect(status().isNotFound());

            verify(ingredientService).getIngredientByName(badName);
    }

    //get ingredient by dietary restriction
        //happy path
    @Test
    public void testGetIngredientByDiet() throws Exception {
        String dietaryRestriction = "Vegan";
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(1);
        ingredient.setName("Tofu");

        when(ingredientService.getIngredientByDiet(anyString())).thenReturn(ingredient);

        mockMvc.perform(get("/ingredients/dietaryRestriction/{dietaryRestriction}", dietaryRestriction)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientId").value(1))
                .andExpect(jsonPath("$.name").value("Tofu"));
    }
        //sad path
    @Test
    public void testGetIngredientByDiet_IngredientNotFound() throws Exception {
        String dietaryRestriction = "Vegan";

        when(ingredientService.getIngredientByDiet(anyString())).thenThrow(new Exception("Ingredient not found"));

        mockMvc.perform(get("/ingredients/dietaryRestriction/{dietaryRestriction}", dietaryRestriction)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    //get list of ingredients that are common allergens
        //happy path
    @Test
    public void testGetIngredientsThatAreCommonAllergens() throws Exception {
        Boolean commonAllergen = true;
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setIngredientId(1);
        ingredient1.setName("Peanut");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setIngredientId(2);
        ingredient2.setName("Milk");

        List<Ingredient> ingredients = Arrays.asList(ingredient1, ingredient2);

        when(ingredientService.getIngredientsThatAreCommonAllergens(anyBoolean())).thenReturn(ingredients);

        mockMvc.perform(get("/ingredients/commonAllergens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commonAllergen)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ingredientId").value(1))
                .andExpect(jsonPath("$[0].name").value("Peanut"))
                .andExpect(jsonPath("$[1].ingredientId").value(2))
                .andExpect(jsonPath("$[1].name").value("Milk"));
    }

    //get all ingredients
        //happy path
    @Test
    public void testGetAllIngredients() throws Exception {
        List<Ingredient> mockIngredients = Arrays.asList(mockIngredient, mockIngredient);

        when(ingredientService.getAllIngredients()).thenReturn(mockIngredients);

        ObjectMapper objectMapper = new ObjectMapper();
        String mockIngredientsJson = objectMapper.writeValueAsString(mockIngredients);

        mockMvc.perform(get("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockIngredientsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockIngredients.size()));

        verify(ingredientService).getAllIngredients();
    }

//UPDATE
    //update ingredient
        //happy path
    @Test
    public void testUpdateIngredient() throws Exception {
        int ingredientId = 1;
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(ingredientId);
        ingredient.setName("Updated Ingredient");

        Ingredient updatedIngredient = new Ingredient();
        updatedIngredient.setIngredientId(ingredientId);
        updatedIngredient.setName("Updated Ingredient");

        when(ingredientService.updateIngredient(anyInt(), any(Ingredient.class))).thenReturn(updatedIngredient);

        mockMvc.perform(put("/ingredients/{ingredientId}", ingredientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ingredient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientId").value(ingredientId))
                .andExpect(jsonPath("$.name").value("Updated Ingredient"));
    }

        //sad path
    @Test
    public void TestUpdateIngredient_IdDoesNotExist() throws Exception {
        when(ingredientService.updateIngredient(anyInt(), any(Ingredient.class))).thenThrow(new Exception("Ingredient with id " + mockIngredient.getIngredientId() + " not found"));

        mockMvc.perform(put("/ingredients/{id}", mockIngredient.getIngredientId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ingredientToJson(mockIngredient)))
                .andExpect(status().isNotFound());

        verify(ingredientService).updateIngredient(any(Integer.class), any(Ingredient.class));
    }

//DELETE
    //delete ingredient
        //happy path
    @Test public void testDeleteIngredient() throws Exception {
        int id = 1;

        doNothing().when(ingredientService).deleteIngredient(anyInt());

        ResultActions resultActions = mockMvc.perform(delete("/ingredients/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));
            resultActions.andExpect(status().isOk());

            verify(ingredientService).deleteIngredient(id);
    }

}
