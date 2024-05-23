package org.example.capstonebackend.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.Ingredient;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.core.Is.is;


@Component
public class IngredientTestUtilities {

    static Ingredient ingredient = new Ingredient();

    public static final Ingredient mockIngredient = createMockIngredient();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Ingredient createMockIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(1);
        ingredient.setName("cheese");
        ingredient.setIngredientFoodGroup("dairy");
        ingredient.setDietaryRestriction("lactose");
        ingredient.setCommonAllergen(true);

        return ingredient;
    }

    public static String ingredientToJson(Ingredient mockIngredient) {
        try {
            return objectMapper.writeValueAsString(mockIngredient);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void compareJsonOutputIngredient(ResultActions resultActions, Ingredient mockIngredient) throws Exception {
        resultActions
                .andExpect(jsonPath("$.ingredientId", is(mockIngredient.getIngredientId())))
                .andExpect(jsonPath("$.name", is(mockIngredient.getName())))
                .andExpect(jsonPath("$.ingredientFoodGroup", is(mockIngredient.getIngredientFoodGroup())))
                .andExpect(jsonPath("$.dietaryRestriction", is(mockIngredient.getDietaryRestriction())))
                .andExpect(jsonPath("$.commonAllergen", is(mockIngredient.getCommonAllergen())));
    }

}
