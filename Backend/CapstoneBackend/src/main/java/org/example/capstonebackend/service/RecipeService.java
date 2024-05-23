package org.example.capstonebackend.service;

import org.example.capstonebackend.model.Category;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.repository.IIngredientRepository;
import org.example.capstonebackend.repository.IRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final IRecipeRepository recipeRepository;
    private final IIngredientRepository ingredientRepository;

    @Autowired
    public RecipeService(IRecipeRepository recipeRepository, IIngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }


//CREATE
    //create recipe
    public Recipe addRecipe(Recipe recipe) throws Exception {
        Optional<Recipe> recipeExists = recipeRepository.findByName(recipe.getName());

        if(recipeExists.isPresent()) {
            throw new Exception("Recipe with name " + recipe.getName() + " already exists");
        }

        return recipeRepository.save(recipe);
    }

    //add ingredient to recipe
    public Recipe  addIngredientToRecipe(Integer recipeId, Integer ingredientId) throws Exception {
        // Retrieve the recipe by its ID or throw an exception if not found
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new Exception ("Recipe with id " + recipeId + " not found"));
        // Retrieve the ingredient by its ID or throw an exception if not found
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> new Exception ("Ingredient with id " + ingredientId + " not found"));

        // Add the ingredient to the recipe's list of ingredients
        recipe.getIngredients().add(ingredient);
        // Add the recipe to the ingredient's list of recipes
        ingredient.getRecipes().add(recipe);

        // Add the ingredient to the recipe
        recipe.getIngredients().add(ingredient);
        // Save the ingredient with updated list of recipes
        ingredientRepository.save(ingredient);
        //Save the recipe with ingredients
        recipeRepository.save(recipe);

        return recipe;
    }

//READ
    //get recipe by name
    public Recipe getRecipeByName(String recipeName) throws Exception {
        return recipeRepository.findByName(recipeName)
                .orElseThrow(() -> new Exception("Recipe with name " + recipeName + " not found"));
    }

    //Get All Recipes in Category
    public List<Recipe> getRecipesByCategory(Category category) {
        return recipeRepository.findByCategory(category);
    }


    //get all recipes
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }


//UPDATE
    //update recipe
    public Recipe updateRecipe(int recipeId, Recipe recipe) throws Exception {
        Recipe oldRecipe = recipeRepository.findById(recipe.getRecipeId()).orElse(null);

        if(oldRecipe == null) {
            throw new Exception("Recipe with id " + recipe.getRecipeId() + " not found");
        }
        oldRecipe.setName(recipe.getName());
        oldRecipe.setRecipeAuthor(recipe.getRecipeAuthor());

        return recipeRepository.save(oldRecipe);
    }
//DELETE
    //delete recipe
    public void deleteRecipe(int id) {
    recipeRepository.deleteById(id);
}

}
