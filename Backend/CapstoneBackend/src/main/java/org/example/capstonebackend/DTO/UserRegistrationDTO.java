package org.example.capstonebackend.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    @NotBlank(message = "User first name is mandatory")
    @Column(name = "firstName", nullable = false, unique = false)
    private String firstName;

    @NotBlank(message = "User last name is mandatory")
    @Column(name = "lastName", nullable = false, unique = false)
    private String lastName;

    @NotBlank(message = "User date of birth is mandatory")
    @Column(name = "dateOfBirth", nullable = false, length = 50, unique = false)
    private String dateOfBirth;

    @NotBlank(message = "User email is mandatory")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Size(min = 8, message="Password must be at least 8 characters long")
    @Column(name="password", nullable = false, length = 255)
    private String password;
}
