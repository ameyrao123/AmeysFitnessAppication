package com.Fitness.AIService.Service;

import com.Fitness.AIService.Model.Activity;
import com.Fitness.AIService.Model.Recommendation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityAIService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity) throws JsonProcessingException {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getSuggestionFromPrompt(prompt);
        log.info("Response from ai: {}", aiResponse);
        return processAIResponse(activity,aiResponse);
    }

    private Recommendation processAIResponse(Activity activity,String aiResponse) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        JsonNode rootNode=mapper.readTree(aiResponse);
        JsonNode textNode=rootNode.path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text");
        String jsonContent=textNode.asText()
                .replaceAll("```json\\n","")
                .replaceAll("\\n```","")
                .trim();

        // log.info("Parsed Response From AI: {}",jsonContent);
        JsonNode rootJsonContent=mapper.readTree(jsonContent);
        JsonNode analysisNode=rootJsonContent.path("analysis");
        StringBuilder fullAnalysis=new StringBuilder();
        addAnalysisSection(fullAnalysis,analysisNode,"overall","Overall:");
        addAnalysisSection(fullAnalysis,analysisNode,"pace","Pace:");
        addAnalysisSection(fullAnalysis,analysisNode,"heart_rate","Heart Rate:");
        addAnalysisSection(fullAnalysis,analysisNode,"calories_burned","Calories Burned:");

        List<String>  improvements=extractImprovements(rootJsonContent.path("improvements"));
        List<String> suggestions=extractSuggestions(rootJsonContent.path("suggestions"));
        String safety=extractSafety(rootJsonContent.path("safety"));
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getActivityType().name())
                .improvements(improvements)
                .suggestions(suggestions)
                .safety(safety).build();

    }
    // copies from gemini
    private String extractSafety(JsonNode safetyNode) {
        StringBuilder sb = new StringBuilder();

        // Check if Gemini returned an array
        if (safetyNode.isArray()) {
            safetyNode.forEach(node -> sb.append(node.asText()).append("\n"));
        } else {
            // Fallback just in case Gemini occasionally returns a plain string
            sb.append(safetyNode.asText());
        }

        return sb.toString().trim();
    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> fullSuggestions=new ArrayList<>();
        if(suggestionsNode.isArray()){
            suggestionsNode.forEach(suggestion->{
                String workout=suggestion.path("workout").asText();
                String description=suggestion.path("description").asText();
                fullSuggestions.add(String.format("%s : %s",workout,description));
            });
        }
        return fullSuggestions.isEmpty()?Collections.singletonList("No Specific Suggestions"):fullSuggestions;
    }

    private List<String> extractImprovements(JsonNode improvementsNode) {
        List<String> fullImprovements=new ArrayList<>();
        if(improvementsNode.isArray()){
            improvementsNode.forEach(improvement->{
                String area=improvement.path("area").asText();
                String detail=improvement.path("recommendation").asText();
                fullImprovements.add(String.format("%s: %s",area,detail));
            });
        }

        return fullImprovements.isEmpty()? Collections.singletonList("No Specific Improvements"):fullImprovements;
    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if(!analysisNode.path(key).isMissingNode()){
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }
    }

    private String createPromptForActivity(Activity activity) {

        // actually i have to give a proper prompt here to extract the text properly
        // i will do it later
        return String.format("""
        Analyze this fitness activity and provide detailed recommendation in the following exact JSON format:
        {
        "analysis": {
        "overall": "Provide a summary of the activity performance here",
        "pace": "Analyze the speed or pace maintained during the activity here",
        "heart_rate": "Comment on the heart rate intensity relative to the activity type here",
        "calories_burned": "Evaluate the energy expenditure for this session here"
        },
        "improvements": [
        {
        "area": "Specify the performance metric or muscle group to focus on (e.g., Stamina, Form, Intensity) here",
        "recommendation": "Provide a specific actionable advice for this area here"
        }
        ],
        "suggestions": [
        {
        "workout": "Suggest a specific exercise or workout type for the next session.",
        "description": "Provide a brief explanation of how this workout helps achieve goals."
        }
        ],
        "safety": {"Provide a general safety tip or precaution.", "Provide a recovery or injury-prevention guideline."}
        }
        
        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Matrices: %s
        
        Provide a detailed analysis focusing on past performance, performance improvements, 
        and next workout suggestions and safety guidelines. Ensure that the response follows 
        the exact JSON format shown above""",
        
        
                activity.getActivityType(),       // %s -> Text representation of the Enum
                activity.getDuration(),           // %d -> Whole number (Integer)
                activity.getCaloriesBurned(),     // %d -> Whole number (Integer)
                activity.getAdditionalMatrics()   // %s -> Map contents printed as textual key-values
        );
    }
}