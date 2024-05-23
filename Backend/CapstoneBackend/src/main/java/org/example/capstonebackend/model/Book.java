package org.example.capstonebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;

    //Creating 1:N relationship between Book and Category
    @OneToMany(mappedBy = "book")
    private List<Category> categories;

    public void setRecipes(List<Recipe> list) {
    }
}

