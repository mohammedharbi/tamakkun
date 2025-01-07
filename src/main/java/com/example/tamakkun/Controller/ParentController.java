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


    @GetMapping("get-my-info")
    public  ResponseEntity getParentById (@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(parentService.getParentById(user.getId()));
    }

    @PostMapping("/parent-register")
    public ResponseEntity parentRegister (@RequestBody @Valid ParentDTO_In parentDTOIn){
        parentService.parentRegister(parentDTOIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Parent registered successfully!"));
    }
    @PutMapping("/update")
    public ResponseEntity updateParent (@AuthenticationPrincipal MyUser user, @RequestBody @Valid ParentDTO_In  parentDTOIn){
        parentService.updateParent(user.getId(), parentDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Parent updated successfully!"));
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteParent (@AuthenticationPrincipal MyUser user ){
        parentService.deleteParent(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Parent deleted successfully!"));
    }

    @GetMapping("get-all-old-bookings-by-parent")
    public ResponseEntity getAllOldBookingsByParent(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(parentService.getAllOldBookingsByParent(user.getId()));
    }
    @GetMapping("get-all-new-bookings-by-parent")
    public ResponseEntity getAllNewBookingsByParent(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(parentService.getAllNewBookingsByParent(user.getId()));
    }
    @GetMapping("get-all-un-reviewed-bookings-by-parent")
    public ResponseEntity getAllUnReviewedBookingsByParent(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(parentService.getAllUnReviewedBookingsByParent(user.getId()));
    }
    @GetMapping("get-all-reviewed-bookings-by-parent")
    public ResponseEntity getAllReviewedBookingsByParent (@AuthenticationPrincipal MyUser user){// need to be tested
        return ResponseEntity.status(200).body(parentService.getAllReviewedBookingsByParent(user.getId()));
    }
    @GetMapping("recommendation-to-parents-by-centre-and-activities")
    public ResponseEntity recommendationToParentsByCentresAndActivities(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(parentService.recommendationToParentsByCentresAndActivities(user.getId()));
    }
    @PutMapping("cancel-booking/booking/{booking_id}")
    public ResponseEntity cancelBooking(@AuthenticationPrincipal MyUser user, @PathVariable Integer booking_id){
        parentService.cancelBooking(user.getId(), booking_id);
        return ResponseEntity.status(200).body(new ApiResponse("Booking cancelled successfully!"));
    }

}
