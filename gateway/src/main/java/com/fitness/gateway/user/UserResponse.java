package com.fitness.gateway.user;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private String id;
    private String keycloakId;
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
