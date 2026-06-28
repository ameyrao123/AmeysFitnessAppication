package com.FitnessApplication.UserService.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String keycloakId;
    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    private String first_name;
    private String last_name;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private UserRole userRole=UserRole.USER;
}
