package com.Fitness.AIService.Controller;

import com.Fitness.AIService.Model.Recommendation;
import com.Fitness.AIService.Service.ServiceRecommendation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendation")

public class RecommendationController {
    private final ServiceRecommendation serviceRecommendation;
    // we have user recommendation as well as activity recommendation
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable String userId){
        return ResponseEntity.ok(serviceRecommendation.getUserRecommendation(userId));
    }
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable String activityId){
        return ResponseEntity.ok(serviceRecommendation.getActivityRecommendation(activityId));
    }
}
