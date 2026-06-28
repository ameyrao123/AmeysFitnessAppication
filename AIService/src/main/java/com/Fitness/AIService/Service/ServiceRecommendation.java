package com.Fitness.AIService.Service;


import com.Fitness.AIService.Model.Recommendation;
import com.Fitness.AIService.Repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRecommendation {
    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(()->new RuntimeException("No Recommendation Found for this Activity"));
    }
}
