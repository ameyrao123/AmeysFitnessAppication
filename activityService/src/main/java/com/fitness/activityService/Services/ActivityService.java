package com.fitness.activityService.Services;

import com.fitness.activityService.DTO.ActivityRequest;
import com.fitness.activityService.DTO.ActivityResponse;
import com.fitness.activityService.Model.Activity;
import com.fitness.activityService.Repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor

public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;

    // this is for rabbit mq communication
    @Value("${rabbitmq.exchange.name}")
    private  String exchange;
    @Value("${rabbitmq.routing.key}")
    private  String routingKey;
    private final RabbitTemplate rabbitTemplate;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {

        boolean isValid=userValidationService.ValidateUser(activityRequest.getUserId());
        if(!isValid) throw new RuntimeException("User Is Invalid");
        Activity activity=Activity.builder()
                .userId(activityRequest.getUserId())
                .activityType(activityRequest.getActivityType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .additionalMatrics(activityRequest.getAdditionalMatrics()).build();

        Activity saveActivity=activityRepository.save(activity);
        // this saveActivity i will send to rabbit mq
        // we want something know as rabbit template
        try{
            // so here when we send this saveActivity , as it is an object
            // spring first checks the do we have jsonjackson converter, if yes
            // then is converts this object into json and stores in the queue
            rabbitTemplate.convertAndSend(exchange,routingKey,saveActivity);
        } catch (Exception e) {
            log.error("failed to publish in rabbit mq");
            throw new RuntimeException(e);
        }
        return mapToActivityResponse(saveActivity);

    }
    private ActivityResponse mapToActivityResponse(Activity activity){
        ActivityResponse activityResponse=new ActivityResponse();
        activityResponse.setActivityType(activity.getActivityType());
        activityResponse.setId(activity.getId());
        activityResponse.setCaloriesBurned(activity.getCaloriesBurned());
        activityResponse.setStartTime(activity.getStartTime());
        activityResponse.setCreatedAt(activity.getCreatedAt());
        activityResponse.setDuration(activity.getDuration());
        activityResponse.setUpdatedAt(activity.getUpdatedAt());
        activityResponse.setAdditionalMatrics(activity.getAdditionalMatrics());
        activityResponse.setUserId(activity.getId());
        return activityResponse;
    }

    public List<ActivityResponse> getUserActivity(String userId) {

       List<Activity>  activityList=activityRepository.findByUserId(userId);
       return activityList.stream()
               .map(this::mapToActivityResponse)
               .collect(Collectors.toList());
    }
}
