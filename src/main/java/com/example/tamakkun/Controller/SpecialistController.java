package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Specialist;
import com.example.tamakkun.Service.SpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tamakkun-system/specialist")

public class SpecialistController {

    private final SpecialistService specialistService;


    @GetMapping("/get-all-specialists")
    public ResponseEntity getAllSpecialists(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(specialistService.getAllSpecialists());
    }

    @PostMapping("/add-specialist")
    public ResponseEntity addSpecialist(@AuthenticationPrincipal MyUser user, @RequestBody @Valid Specialist specialist){

        specialistService.addSpecialist(user.getId(), specialist);
        return ResponseEntity.status(200).body(new ApiResponse("Specialist added successfully!"));
    }
    @PutMapping("/update-specialist/{specialist_id}")
    public ResponseEntity updateSpecialist(@PathVariable Integer specialist_id, @RequestBody @Valid Specialist specialist, @AuthenticationPrincipal MyUser user){
        specialistService.updateSpecialist(specialist_id, specialist, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Specialist updated successfully!"));

    }
    @DeleteMapping("/delete-specialist/{specialist_id}")
    public ResponseEntity deleteSpecialist(@PathVariable Integer specialist_id, @AuthenticationPrincipal MyUser user){
        specialistService.deleteSpecialist(specialist_id, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Specialist deleted successfully!"));

    }

    @GetMapping("/get-specialist-byDisability/{disabilityType}")
    public ResponseEntity getSpecialistsBySupportedDisability(@AuthenticationPrincipal MyUser user, @PathVariable String disabilityType){
        List<Specialist> specialists= specialistService.getSpecialistsBySupportedDisability(user.getId(), disabilityType);
        return ResponseEntity.status(200).body(specialists);
    }


    @GetMapping("/get-specialist-byPhoneNumber/{phoneNumber}")
    public ResponseEntity getSpecialistByPhoneNumber(@PathVariable String phoneNumber, @AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(specialistService.getSpecialistByPhoneNumber(phoneNumber, user.getId()));
    }


}
