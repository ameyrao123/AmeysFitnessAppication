package com.fitness.gateway.user;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Data
@Slf4j
public class UserService {

    private final WebClient webClient;

    public Mono<Boolean> validateUser(String userId) {

        log.info("Calling User Validation API for userID: {}", userId);

        return webClient.get()
                .uri("api/users/{userId}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(WebClientResponseException.class, e -> {

                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(
                                new RuntimeException("User not found"));
                    }

                    else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(
                                new RuntimeException("Bad request"));
                    }

                    return Mono.error(
                            new RuntimeException("Unexpected error"));
                });
    }

    public Mono<UserResponse> registerUser(RegisterRequest registerRequest) {
        log.info("Calling User UserRegistration API for email: {}", registerRequest.getEmail());

        return webClient.post()
                .uri("api/users/register")
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {

                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(
                                new RuntimeException("Bad Request"));
                    }

                    else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                        return Mono.error(
                                new RuntimeException("Internal ServerError"));
                    }

                    return Mono.error(
                            new RuntimeException("Unexpected error"));
                });
    }
}