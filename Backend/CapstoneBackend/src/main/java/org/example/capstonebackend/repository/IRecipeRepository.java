package org.example.capstonebackend.repository;

import org.example.capstonebackend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findByName(String recipeName);

    Optional<Object> findByAuthor(String recipeAuthor);
}
