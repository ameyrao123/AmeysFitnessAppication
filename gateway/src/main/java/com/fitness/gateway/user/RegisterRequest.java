package com.fitness.gateway.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    // just a message
    @NotBlank(message = "Email Is Required")
    @Email(message = "Invalid Email")
    private String email;
    @NotBlank(message = "Email Is Required")
    @Size(min=6,message = "Password must have at least have 6 characters")
    private String password;
    private String keycloakId;
    @NotBlank(message = "First Name Is Required")
    private String firstName;
    private String lastName;
}
