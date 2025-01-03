package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.Activity;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tamakkun-system/activity")

public class ActivityController {

    private final ActivityService activityService;


    @GetMapping("/get-activities")
    public ResponseEntity getAllActivities(){
        return ResponseEntity.status(200).body(activityService.getAllActivities());
    }

    @PostMapping("/add-activity/{centre_id}")
    public ResponseEntity addActivity(@PathVariable Integer centre_id, @RequestBody @Valid Activity activity){
        activityService.addActivity(centre_id, activity);
        return ResponseEntity.status(200).body(new ApiResponse("Activity added successfully"));
    }

    @PutMapping("/update-activity/{activity_id}/{centre_id}")
    public ResponseEntity updateActivity(@PathVariable Integer activity_id,@RequestBody @Valid Activity activity, @PathVariable Integer centre_id){
        activityService.updateActivity(activity_id, activity, centre_id);
        return ResponseEntity.status(200).body(new ApiResponse("Activity updated successfully"));

    }
    @DeleteMapping("/delete-activity/{activity_id}/{centre_id}")
    public ResponseEntity deleteActivity(@PathVariable Integer activity_id, @PathVariable Integer centre_id){
        activityService.deleteActivity(activity_id, centre_id);
        return ResponseEntity.status(200).body(new ApiResponse("Activity deleted successfully"));


    }












}
