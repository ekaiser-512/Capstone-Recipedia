package org.example.capstonebackend.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.Recipe;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.core.Is.is;


@Component
public class RecipeTestUtilites {

    static Recipe recipe = new Recipe();

    public static final Recipe mockRecipe = createMockRecipe();

    public static final Ingredient mockIngredient = createMockIngredient();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Recipe createMockRecipe() {
        Recipe Recipe = new Recipe();
        recipe.setRecipeId(1);
        recipe.setName("marinara sauce");
        recipe.setRecipeAuthor("Grandma Pat");

        return recipe;
    }

    private static Ingredient createMockIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(1);
        ingredient.setName("cheese");
        ingredient.setIngredientFoodGroup("dairy");
        ingredient.setDietaryRestriction("lactose");
        ingredient.setCommonAllergen(true);

        return ingredient;
    }

    public static String recipeToJson(Recipe mockRecipe) {
        try {
            return objectMapper.writeValueAsString(mockRecipe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void compareJsonOutputRecipe(ResultActions resultActions, Recipe mockRecipe) throws Exception {
        resultActions
                .andExpect(jsonPath("$.recipeId", is(mockRecipe.getRecipeId())))
                .andExpect(jsonPath("$.name", is(mockRecipe.getName())))
                .andExpect(jsonPath("$.recipeAuthor", is(mockRecipe.getRecipeAuthor())));
    }

}