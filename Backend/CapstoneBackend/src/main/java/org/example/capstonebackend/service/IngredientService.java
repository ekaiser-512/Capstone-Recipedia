package org.example.capstonebackend.service;

import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.Recipe;
import org.example.capstonebackend.repository.IIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IngredientService {
    private final IIngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IIngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

//CREATE
    //add ingredient
    public Ingredient addIngredient(Ingredient ingredient) throws Exception {
        Optional<Ingredient> ingredientExists = ingredientRepository.findByName(ingredient.getName());

        if(ingredientExists.isPresent()) {
            throw new Exception("Ingredient with name " + ingredient.getName() + " already exists");
        }

        return ingredientRepository.save(ingredient);
    }

//READ
    //get ingredient by name
    public Ingredient getIngredientByName(String ingredientName) throws Exception {
        return ingredientRepository.findByName(ingredientName)
                .orElseThrow(() -> new Exception("Ingredient with name " + ingredientName + " not found"));
    }

    //get ingredient by dietary restriction
    public Ingredient getIngredientByDiet(String dietaryRestriction) throws Exception {
            return ingredientRepository.findByDietaryRestriction(dietaryRestriction)
                    .orElseThrow(() -> new Exception("Ingredient with dietary restriction " + dietaryRestriction + " not found"));
    }

    //get list of ingredients that are common allergens
    public List<Ingredient> getIngredientsThatAreCommonAllergens(Boolean commonAllergen) {
        return ingredientRepository.findByCommonAllergen(commonAllergen);
    }

    //get all ingredients in recipe
    public List<Ingredient> getIngredientsInRecipe(Recipe recipe) {
        return ingredientRepository.findByRecipes(recipe);
    }

    //get all ingredients
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

//UPDATE
    //update ingredient
    public Ingredient updateIngredient(int id, Ingredient ingredient) throws Exception {

        Ingredient oldIngredient = ingredientRepository.findById(id).orElse(null);

        if(oldIngredient == null) {
            throw new Exception("Ingredient with id " + id + " not found");
        }

        oldIngredient.setName(ingredient.getName());
        oldIngredient.setIngredientFoodGroup(ingredient.getIngredientFoodGroup());
        oldIngredient.setCommonAllergen(ingredient.getCommonAllergen());
        oldIngredient.setDietaryRestriction(ingredient.getDietaryRestriction());

        return ingredientRepository.save(oldIngredient);
    }

//DELETE
    //delete ingredient
    public void deleteIngredient(int id) {
        ingredientRepository.deleteById(id);
    }

}
