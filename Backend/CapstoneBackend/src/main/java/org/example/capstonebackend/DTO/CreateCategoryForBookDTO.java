package org.example.capstonebackend.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryForBookDTO {

    @NotBlank(message = "Category title is mandatory")
    @Column(name = "title", nullable = false, unique = true)
    private String title;
}
