package com.FitnessApplication.UserService.Controller;

import com.FitnessApplication.UserService.DTO.RegisterRequest;
import com.FitnessApplication.UserService.DTO.UserResponse;
import com.FitnessApplication.UserService.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest ){
        return ResponseEntity.ok(userService.Register(registerRequest));
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> existsUserById(@PathVariable String userId){
        return ResponseEntity.ok(userService.existsUserById(userId));
    }

}
