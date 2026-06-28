package com.FitnessApplication.UserService.Service;

import com.FitnessApplication.UserService.DTO.RegisterRequest;
import com.FitnessApplication.UserService.DTO.UserResponse;
import com.FitnessApplication.UserService.Model.User;
import com.FitnessApplication.UserService.Repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse Register(@Valid RegisterRequest registerRequest) {

        // i am trying to add some validation

        if(userRepository.existsByEmail(registerRequest.getEmail())){
            User existingUser=userRepository.findByEmail(registerRequest.getEmail());
            UserResponse userResponse=new UserResponse();
            userResponse.setId(existingUser.getId());
            userResponse.setKeycloakId(existingUser.getKeycloakId());
            userResponse.setPassword(existingUser.getPassword());
            userResponse.setEmail(existingUser.getEmail());
            userResponse.setFirst_name(existingUser.getFirst_name());
            userResponse.setLast_name(existingUser.getLast_name());
            userResponse.setCreatedAt(existingUser.getCreatedAt());
            userResponse.setUpdatedAt(existingUser.getUpdatedAt());

            return userResponse;
        }


        User user=new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setKeycloakId(registerRequest.getKeycloakId());
        user.setFirst_name(registerRequest.getFirstName());
        user.setLast_name(registerRequest.getLastName());
        // now i want to save this in data base ?? so creating user repository
        User savedUser=userRepository.save(user);
        UserResponse userResponse=new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setKeycloakId(savedUser.getKeycloakId());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setFirst_name(savedUser.getFirst_name());
        userResponse.setLast_name(savedUser.getLast_name());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());

        return userResponse;

    }

    public UserResponse getUserProfile(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User Does Not Exist"));
        UserResponse userResponse=new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirst_name(user.getFirst_name());
        userResponse.setLast_name(user.getLast_name());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        return userResponse;
    }

    public Boolean existsUserById(String userId) {
        log.info("Calling User Validation API for userID: {userId}",userId);
        return userRepository.existsBykeycloakId(userId);
    }
}
