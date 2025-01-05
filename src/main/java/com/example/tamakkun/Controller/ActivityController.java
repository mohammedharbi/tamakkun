package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_Out.ActivityDTO_Out;
import com.example.tamakkun.Model.Activity;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tamakkun-system/activity")

public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/get-activities")
    public ResponseEntity getAllActivities(){
        return ResponseEntity.status(200).body(activityService.getAllActivities());
    }

    @PostMapping("/add-activity")
    public ResponseEntity addActivity(@AuthenticationPrincipal MyUser user, @RequestBody @Valid Activity activity){
        activityService.addActivity(user.getId(), activity);
        return ResponseEntity.status(200).body(new ApiResponse("Activity added successfully"));
    }

    @PutMapping("/update-activity/{activity_id}")
    public ResponseEntity updateActivity(@PathVariable Integer activity_id,@RequestBody @Valid Activity activity, @AuthenticationPrincipal MyUser user){
        activityService.updateActivity(activity_id, activity, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Activity updated successfully"));

    }
    @DeleteMapping("/delete-activity/{activity_id}")
    public ResponseEntity deleteActivity(@PathVariable Integer activity_id, @AuthenticationPrincipal MyUser user){
        activityService.deleteActivity(activity_id, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Activity deleted successfully"));


    }
    @GetMapping("/activities-by-disabilityType/{disabilityType}")
    public ResponseEntity getActivitiesByDisabilityType(@AuthenticationPrincipal MyUser user, @PathVariable String disabilityType){
        List<ActivityDTO_Out> activities = activityService.getActivitiesByDisabilityType(user.getId(), disabilityType);
        return ResponseEntity.status(200).body(activities);

    }


    @GetMapping("/description-audio/{activity_id}")
    public ResponseEntity<byte[]> getActivityDescriptionAsAudio(@PathVariable Integer activity_id) {
        try {
            // تحويل الوصف إلى صوت
            byte[] audioData = activityService.getActivityDescriptionAsAudio(activity_id);

            // إعداد الاستجابة مع ملف صوت
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=centre_description.mp3");

            return new ResponseEntity<>(audioData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Failed to generate audio for the provided centre ID: " + e.getMessage()).getBytes());
        }}










}
