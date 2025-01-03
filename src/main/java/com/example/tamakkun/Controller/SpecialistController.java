package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.Specialist;
import com.example.tamakkun.Service.SpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("add-supported-disability/centre/{centre_id}/specialist/{specialist_id}/supportedDisability/{supportedDisability}")
    public ResponseEntity addSupportedDisability(@PathVariable Integer centre_id, @PathVariable Integer specialist_id, @PathVariable String supportedDisability){
        specialistService.addSupportedDisability(centre_id, specialist_id, supportedDisability);
        return ResponseEntity.status(200).body(new ApiResponse("Specialist added successfully!"));
    }


}
