package org.example.capstonebackend.repository;

import org.example.capstonebackend.model.Ingredient;
import org.example.capstonebackend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IIngredientRepository extends JpaRepository<Ingredient, Integer> {
    Optional<Ingredient> findByName(String ingredientName);

    Optional<Ingredient> findByDietaryRestriction(String dietaryRestriction);

    List<Ingredient> findByCommonAllergen(Boolean commonAllergen);

    List<Ingredient> findByRecipes(Recipe recipe);
}
