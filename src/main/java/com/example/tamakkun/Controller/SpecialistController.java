package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.Specialist;
import com.example.tamakkun.Service.SpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tamakkun-system/specialist")

public class SpecialistController {

    private final SpecialistService specialistService;



    @GetMapping("/get-specialists")
    public ResponseEntity getAllSpecialists(){
        return ResponseEntity.status(200).body(specialistService.getAllSpecialists());
    }

    //will be @AuthPrinciple in security
        @PostMapping("/add-specialist/{centre_id}")
    public ResponseEntity addSpecialist(@PathVariable Integer centre_id, @RequestBody @Valid Specialist specialist){

        specialistService.addSpecialist(centre_id, specialist);
        return ResponseEntity.status(200).body(new ApiResponse("Specialist added successfully!"));
    }

    @PutMapping("/update-specialist/{specialist_id}/{centre_id}")
    public ResponseEntity updateSpecialist(@PathVariable Integer specialist_id, @RequestBody @Valid Specialist newSpecialist, @PathVariable Integer centre_id){

        specialistService.updateSpecialist(specialist_id, newSpecialist, centre_id);
        return ResponseEntity.status(200).body(new ApiResponse("Specialist updated successfully!"));

    }

    @DeleteMapping("/delete-specialist/{specialist_id}/{centre_id}")
    public ResponseEntity deleteSpecialist(@PathVariable Integer specialist_id, @PathVariable Integer centre_id){
        specialistService.deleteSpecialist(specialist_id, centre_id);
        return ResponseEntity.status(200).body(new ApiResponse("Specialist deleted successfully!"));

    }

    @GetMapping("/get-specialist-byDisability/{disabilityType}")
    public ResponseEntity getSpecialistsBySupportedDisability(@PathVariable String disabilityType){
        List<Specialist> specialists= specialistService.getSpecialistsBySupportedDisability(disabilityType);
        return ResponseEntity.status(200).body(specialists);
    }

    @GetMapping("/get-specialist-byName/{name}/{centreId}")
    public ResponseEntity getSpecialistByNameAndCentreId(@PathVariable String name, @PathVariable Integer centreId){
        return ResponseEntity.status(200).body(specialistService.getSpecialistByNameAndCentreId(name, centreId));
    }


}
