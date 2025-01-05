package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_In.ParentDTO_In;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tamakkun-system/parent")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;


    @GetMapping("get-parent-by-id/{user_id}")
    public  ResponseEntity getParentById (@PathVariable Integer user_id){
        return ResponseEntity.status(200).body(parentService.getParentById(user_id));
    }

    @PostMapping("/parent-register")
    public ResponseEntity register (@RequestBody @Valid ParentDTO_In parentDTOIn){
        parentService.register(parentDTOIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Parent registered successfully!"));
    }
    @PutMapping("/update/{user_id}")
    public ResponseEntity update (@PathVariable Integer user_id, @RequestBody @Valid ParentDTO_In  parentDTOIn){
        parentService.update(user_id,parentDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }
    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity delete (@PathVariable Integer user_id ){
        parentService.delete(user_id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }

    @GetMapping("get-all-old-bookings-by-parent/parent/{parent_id}")
    public ResponseEntity getAllOldBookingsByParent(@PathVariable Integer parent_id){
        return ResponseEntity.status(200).body(parentService.getAllOldBookingsByParent(parent_id));
    }
    @GetMapping("get-all-new-bookings-by-parent/parent/{parent_id}")
    public ResponseEntity getAllNewBookingsByParent(@PathVariable Integer parent_id){
        return ResponseEntity.status(200).body(parentService.getAllNewBookingsByParent(parent_id));
    }
    @GetMapping("get-all-un-reviewed-bookings-by-parent/parent/{parent_id}")
    public ResponseEntity getAllUnReviewedBookingsByParent(@PathVariable Integer parent_id){
        return ResponseEntity.status(200).body(parentService.getAllUnReviewedBookingsByParent(parent_id));
    }
    @GetMapping("get-all-reviewed-bookings-by-parent/parent/{parent_id}")
    public ResponseEntity getAllReviewedBookingsByParent (@PathVariable Integer parent_id){// need to be tested
        return ResponseEntity.status(200).body(parentService.getAllReviewedBookingsByParent(parent_id));
    }
    @GetMapping("recommendation-to-parents-by-centre-and-activities/parent/{parent_id}")
    public ResponseEntity recommendationToParentsByCentresAndActivities(@PathVariable Integer parent_id){
        return ResponseEntity.status(200).body(parentService.recommendationToParentsByCentresAndActivities(parent_id));
    }
    @PutMapping("cancel-booking/parent/{parent_id}/booking/{booking_id}")
    public ResponseEntity cancelBooking(@PathVariable Integer parent_id, @PathVariable Integer booking_id){
        parentService.cancelBooking(parent_id,booking_id);
        return ResponseEntity.status(200).body(new ApiResponse("booking cancelled"));
    }

}
