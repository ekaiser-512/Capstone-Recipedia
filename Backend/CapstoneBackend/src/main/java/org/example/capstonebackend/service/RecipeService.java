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
