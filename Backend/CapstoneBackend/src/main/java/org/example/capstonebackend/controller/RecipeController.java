package org.example.capstonebackend.controller;

import org.apache.coyote.Response;
import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.repository.IIngredientRepository;
import org.example.capstonebackend.repository.IRecipeRepository;
import org.example.capstonebackend.service.IngredientService;
import org.example.capstonebackend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    IRecipeRepository recipeRepository;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    IIngredientRepository ingredientRepository;


//CREATE
    //create recipe
    @PostMapping("/recipes")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) throws Exception {
        try {
            Recipe addedRecipe = recipeService.addRecipe(recipe);
            return ResponseEntity.ok(addedRecipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //add ingredient to recipe
    @PostMapping("recipes/{recipeId}/ingredients/{ingredientId}")
    public ResponseEntity<Recipe> addIngredientToRecipe(@PathVariable Integer recipeId, @PathVariable Integer ingredientId) throws Exception {
        try {
            // Attempt to add the ingredient to the recipe
            Recipe recipe = recipeService.addIngredientToRecipe(recipeId, ingredientId);
            // If successful, return OK (200) response with the updated recipe
            return ResponseEntity.ok().body(recipe);
        } catch (Exception e) {
            // If an exception occurs (e.g., recipe or ingredient not found), return Not Found (404) response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

//READ
    //get recipe by name
    @GetMapping("/recipes/recipeName/{recipeName}")
    public ResponseEntity<Recipe> getRecipeByName(@PathVariable String recipeName) {
        try {
            Recipe recipe = recipeService.getRecipeByName(recipeName);
            return ResponseEntity.ok(recipe);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //todo get recipes by author
//    @GetMapping("/recipes/recipeAuthor/{recipeAuthor}")
//    public ResponseEntity<Recipe> getRecipeByAuthor(@PathVariable String recipeAuthor) {
//        try {
//            Recipe recipe = recipeService.getRecipeByAuthor(recipeAuthor);
//            return ResponseEntity.ok(recipe);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    //todo get ingredients in recipe

    //get all recipes
    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return recipes;
    }

//UPDATE
    //update recipe
    @PutMapping("/recipes/{recipeId}")
    public ResponseEntity<?> updateRecipe(@PathVariable int recipeId, @RequestBody Recipe recipe) throws Exception {
        try {
            Recipe updateRecipe = recipeService.updateRecipe(recipeId, recipe);
            return ResponseEntity.ok(updateRecipe);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//DELETE
    //delete recipe
@DeleteMapping("recipes/{recipeId}")
public void deleteRecipe(@PathVariable int id) {
    recipeService.deleteRecipe(id);
}

}
