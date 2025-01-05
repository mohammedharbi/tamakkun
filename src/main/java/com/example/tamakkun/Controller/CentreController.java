package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_In.CentreDTO_In;
import com.example.tamakkun.DTO_Out.CentreDTO_Out;
import com.example.tamakkun.Service.CentreService;
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

    @GetMapping("/filter-centres-byPrices/{minPrice}/{maxPrice}")
    public ResponseEntity filterCentresByPrice(
            @PathVariable Double minPrice,
            @PathVariable Double maxPrice) {
        List<CentreDTO_Out> filteredCentres = centreService.filterCentresByPrice(minPrice, maxPrice);
        return ResponseEntity.status(200).body(filteredCentres);
    }


    @GetMapping("/get-top5-center-by-avrRating")
    public ResponseEntity getTop5CenterByAvrRating (){
        return ResponseEntity.status(200).body(centreService.getTop5CenterByAvrRating());
    }


    @GetMapping("/description-audio/{centreId}")
    public ResponseEntity<byte[]> getCentreDescriptionAsAudio(@PathVariable Integer centreId) {
        try {
            // تحويل الوصف إلى صوت
            byte[] audioData = centreService.getCentreDescriptionAsAudio(centreId);

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
