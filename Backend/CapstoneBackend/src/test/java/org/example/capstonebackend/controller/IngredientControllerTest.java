package org.example.capstonebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import net.bytebuddy.matcher.ElementMatchers;
import org.example.capstonebackend.components.UserTestUtilities;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.User;
import org.example.capstonebackend.repository.IIngredientRepository;
import org.example.capstonebackend.repository.IUserRepository;
import org.example.capstonebackend.service.IngredientService;
import org.example.capstonebackend.service.UserService;
import org.example.capstonebackend.service.UserServiceTest;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.example.capstonebackend.components.IngredientTestUtilities.*;
import static org.example.capstonebackend.components.UserTestUtilities.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.delete;
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
    public void testAddIngredient_IngredientExists() throws Exception {
        when(ingredientService.addIngredient(any())).thenThrow(new Exception("Ingredient with id " + mockIngredient.getIngredientId() + " already exists"));

        mockMvc.perform(post("/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ingredientToJson(mockIngredient)))
                .andExpect(status().isBadRequest());

        verify(ingredientService.addIngredient(any()));
    }

//READ
    //get ingredient by name
        //happy path
    @Test
    public void testGetIngredientByName() throws Exception {
        String ingredientName = "cheese";

        when(ingredientService.getIngredientByName(ingredientName)).thenReturn(mockIngredient);

        ResultActions resultActions = mockMvc.perform(get("/ingredients/ingredientName/{ingredientName}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ingredientToJson(mockIngredient)));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientName", is("cheese")))
                .andExpect(jsonPath("$.ingredientFoodGroup", is("dairy")))
                .andExpect(jsonPath("$.dietaryRestriction", is("lactose")))
                .andExpect(jsonPath("$.commonAllergen", is(true)));

        verify(ingredientService).getIngredientByName(ingredientName);
    }
        //sad path
    @Test
    public void testGetIngredientByName_IngredientNotFound() throws Exception {
        String badName = "unicorn blood";
        when(ingredientService.getIngredientByName(badName)).thenThrow(new Exception("Ingredient with name " + badName + " does not exist"));

        ResultActions resultActions = mockMvc.perform(get("/ingredients/ingredientName/{ingredientName", badName)
                .contentType(MediaType.APPLICATION_JSON));
            resultActions.andExpect(status().isNotFound());

            verify(ingredientService).getIngredientByName(badName);
    }

    //get ingredient by dietary restriction
        //happy path
        //sad path

    //get list of ingredients that are common allergens
        //happy path
        //sad path

//UPDATE
    //update ingredient
        //happy path
        //sad path

//DELETE
    //delete ingredient
        //happy path

}
