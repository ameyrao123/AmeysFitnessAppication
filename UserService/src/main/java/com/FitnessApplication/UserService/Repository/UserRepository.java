package com.FitnessApplication.UserService.Repository;

import com.FitnessApplication.UserService.Model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByEmail(@NotBlank(message = "Email Is Required") @Email(message = "Invalid Email") String email);

    Boolean existsBykeycloakId(String userId);

    User findByEmail(@NotBlank(message = "Email Is Required") @Email(message = "Invalid Email") String email);
}
