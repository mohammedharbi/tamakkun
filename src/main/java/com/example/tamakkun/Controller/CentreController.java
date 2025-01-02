package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_In.CentreDTO_In;
import com.example.tamakkun.Service.CentreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tamakkun-system/centre")

public class CentreController {

    private final CentreService centreService;


    @PostMapping("/centre-register")
    public ResponseEntity centreRegister(@RequestBody @Valid CentreDTO_In centreDTOIn){

        centreService.centreRegister(centreDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Centre registered successfully!"));
    }

    @GetMapping("/get-centres")
    public ResponseEntity getAllCentres(){

        return ResponseEntity.status(200).body(centreService.getAllCentres());

    }

    @PutMapping("/update-centre/{centre_id}")
    public ResponseEntity updateCentre(@PathVariable Integer centre_id, @RequestBody @Valid CentreDTO_In centreDTOIn, @AuthenticationPrincipal Integer user_id){

        centreService.updateCentre(centre_id, centreDTOIn, user_id);
        return ResponseEntity.status(200).body(new ApiResponse("Centre updated successfully!"));

    }
    @DeleteMapping("/delete-centre/{centre_id}")
    public ResponseEntity deleteCentre(@PathVariable Integer centre_id ){
        centreService.deleteCentre(centre_id);
        return ResponseEntity.status(200).body(new ApiResponse("Centre deleted successfully!"));

    }








}
