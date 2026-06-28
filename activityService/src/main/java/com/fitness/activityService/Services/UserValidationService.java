package com.fitness.activityService.Services;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Data
@Slf4j
public class UserValidationService {

    private final WebClient webClient;
    public boolean ValidateUser(String UserId){
       try{
           // this code is used to call restapi
           log.info("Calling User Validation API for userID: {userId}",UserId);
           return Boolean.TRUE.equals(webClient.get()
                   .uri("api/users/{UserId}/validate", UserId)
                   .retrieve()
                   .bodyToMono(Boolean.class)
                   .block());
       }
       catch(WebClientResponseException e){
           throw  e;
       }
    }
}
