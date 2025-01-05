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

    @PostMapping("/add-activity/{centre_id}")
    public ResponseEntity addActivity(@PathVariable Integer centre_id, @RequestBody @Valid Activity activity){
        activityService.addActivity(centre_id, activity);
        return ResponseEntity.status(200).body(new ApiResponse("Activity added successfully!"));
    }

    @PutMapping("/update-activity/{activity_id}/{centre_id}")
    public ResponseEntity updateActivity(@PathVariable Integer activity_id,@RequestBody @Valid Activity activity, @PathVariable Integer centre_id){
        activityService.updateActivity(activity_id, activity, centre_id);
        return ResponseEntity.status(200).body(new ApiResponse("Activity updated successfully!"));

    }
    @DeleteMapping("/delete-activity/{activity_id}/{centre_id}")
    public ResponseEntity deleteActivity(@PathVariable Integer activity_id, @PathVariable Integer centre_id){
        activityService.deleteActivity(activity_id, centre_id);
        return ResponseEntity.status(200).body(new ApiResponse("Activity deleted successfully!"));


    }
    @GetMapping("/activities-by-disabilityType/{disabilityType}")
    public ResponseEntity getActivitiesByDisabilityType(@PathVariable String disabilityType){
        List<ActivityDTO_Out> activities = activityService.getActivitiesByDisabilityType(disabilityType);
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
