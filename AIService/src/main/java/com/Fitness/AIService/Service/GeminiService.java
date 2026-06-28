package com.Fitness.AIService.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


// this service will be used by another ai service inside service class to get the respose from gemini
@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini.api.url}")
    private String geminiUrl;
    @Value("${gemini.api.key}")
    private String geminiKey;
    private  final WebClient webClient;



    public String getSuggestionFromPrompt(String prompt){
        Map<String,Object> requestBody=Map.of(
                "contents", new Object[]{
                        Map.of("parts",new Object[]{
                            Map.of("text", prompt)
                    })
                });
        String response=webClient.post()
                .uri(geminiUrl+"key="+geminiKey)
                .header("Content-Type","application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }
}
