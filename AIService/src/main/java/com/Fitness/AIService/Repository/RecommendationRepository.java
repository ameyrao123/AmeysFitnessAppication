package com.Fitness.AIService.Repository;

import com.Fitness.AIService.Model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation,String> {

    List<Recommendation> findByUserId(String userId);

    Optional<Recommendation> findByActivityId(String activityId);
}
