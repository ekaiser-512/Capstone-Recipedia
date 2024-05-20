package org.example.capstonebackend.repository;

import org.example.capstonebackend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecipeRepository extends JpaRepository<Recipe, Integer> {
}
