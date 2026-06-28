package com.fitness.activityService.Model;

import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Document(collection = "Activity")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    private String id;
    private String userId;
    private ActivityType activityType;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

    @Field("metrics")
    private Map<String, Object> additionalMatrics;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
