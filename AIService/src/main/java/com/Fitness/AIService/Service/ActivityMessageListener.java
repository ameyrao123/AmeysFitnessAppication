package com.Fitness.AIService.Service;

import com.Fitness.AIService.Model.Activity;
import com.Fitness.AIService.Model.Recommendation;
import com.Fitness.AIService.Repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;
    private final RecommendationRepository recommendationRepository;

    @RabbitListener(queues ="activity.queue")
    public void processActivity(Activity activity){
        try {
            log.info("Received activity for processing: {}", activity.getId());
//            log.info("GeneratedRecommendation: {}", activityAIService.generateRecommendation(activity));
            Recommendation recommendation=activityAIService.generateRecommendation(activity);
            log.info("Attempting to save recommendation...");
            recommendationRepository.save(recommendation);
            log.info("Successfully saved recommendation!");

        } catch (Exception e) {
            // Catching the error stops the infinite loop!
            log.error("Message processing failed. Discarding poisoned message: {}", e);
        }
    }
}