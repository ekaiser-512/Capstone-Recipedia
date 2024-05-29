package org.example.capstonebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message="Title is mandatory")
    @Size(min=3, max = 50, message="Title must be between 3 and 50 characters")
    @Column(name="title", nullable = false, length = 50, unique = true)
    private String title;

    //Creating 1:N relationship between Book and Category
    @OneToMany(mappedBy = "book")
    private List<Category> categories;

    public void setRecipes(List<Recipe> list) {
    }
}

