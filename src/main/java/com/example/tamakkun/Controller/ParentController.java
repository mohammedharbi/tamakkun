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


    @GetMapping("get-parent-by-id")
    public  ResponseEntity getParentById (@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(parentService.getParentById(myUser.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid ParentDTO_In parentDTOIn){
        parentService.register(parentDTOIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Parent registered successfully!"));
    }
    @PutMapping("/update")
    public ResponseEntity update (@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid ParentDTO_In  parentDTOIn){
        parentService.update(myUser.getId(),parentDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete (@AuthenticationPrincipal MyUser myUser ){
        parentService.delete(myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
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
