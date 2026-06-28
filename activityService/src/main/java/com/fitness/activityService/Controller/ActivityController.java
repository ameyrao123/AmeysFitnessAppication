package com.fitness.activityService.Controller;

import com.fitness.activityService.DTO.ActivityRequest;
import com.fitness.activityService.DTO.ActivityResponse;
import com.fitness.activityService.Services.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private ActivityService activityService;

    @PostMapping("/fitness")
    ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest){
        return ResponseEntity.ok(activityService.trackActivity(activityRequest));
    }
    @GetMapping("/fitness")
    ResponseEntity<List<ActivityResponse>> getUserActivity(@RequestHeader("X-User-Id") String userId){
        return ResponseEntity.ok(activityService.getUserActivity(userId));
    }

}
