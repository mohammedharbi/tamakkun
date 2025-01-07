package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_In.CentreDTO_In;
import com.example.tamakkun.DTO_Out.ActivityDTO_Out;
import com.example.tamakkun.DTO_Out.CentreDTO_Out;
import com.example.tamakkun.DTO_Out.SpecialistDTO_Out;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Specialist;
import com.example.tamakkun.Service.CentreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
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

    //By admin only
    @GetMapping("/get-all-centres")
    public ResponseEntity getAllCentres(){

        return ResponseEntity.status(200).body(centreService.getAllCentres());

    }
    @GetMapping("/get-centres")
    public ResponseEntity getCentres(){
        return ResponseEntity.status(200).body(centreService.getCentres());
    }

    @PutMapping("/update-centre")
    public ResponseEntity updateCentre(@RequestBody @Valid CentreDTO_In centreDTOIn, @AuthenticationPrincipal MyUser user){

        centreService.updateCentre(centreDTOIn, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Centre updated successfully!"));

    }
    @DeleteMapping("/delete-centre")
    public ResponseEntity deleteCentre(@AuthenticationPrincipal MyUser user){
        centreService.deleteCentre(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Centre deleted successfully!"));

    }

    @GetMapping("/filter-centres-byPrices/{minPrice}/{maxPrice}")
    public ResponseEntity filterCentresByPrice(
            @AuthenticationPrincipal MyUser user,
            @PathVariable Double minPrice,
            @PathVariable Double maxPrice) {
        List<CentreDTO_Out> filteredCentres = centreService.filterCentresByPrice(user.getId(), minPrice, maxPrice);
        return ResponseEntity.status(200).body(filteredCentres);
    }

    @GetMapping("/centre-by-name/{name}")
    public ResponseEntity getCentreByName(@AuthenticationPrincipal MyUser user, @PathVariable String name) {
        CentreDTO_Out centreDTOOut = centreService.getCentreByName(user.getId(), name);
        return ResponseEntity.status(200).body(centreDTOOut);
    }


    @GetMapping("/centres-by-address/{address}")
    public ResponseEntity getCentresByAddress(@AuthenticationPrincipal MyUser user,@PathVariable String address){
        List<CentreDTO_Out>centres = centreService.getCentresByAddress(user.getId(), address);

        return ResponseEntity.status(200).body(centres);

    }


    @GetMapping("/centres-by-hours-range/{startOpening}/{endClosing}")
    public ResponseEntity getCentresByHoursRange(@AuthenticationPrincipal MyUser user,@PathVariable LocalTime startOpening,@PathVariable LocalTime endClosing ){
        List<CentreDTO_Out>centres = centreService.getCentresByHoursRange(user.getId(), startOpening, endClosing);
        return ResponseEntity.status(200).body(centres);

    }

    @GetMapping("/all-specialists-byCentre")
    public ResponseEntity getSpecialistsByCentre(@AuthenticationPrincipal MyUser user){
        List<Specialist> specialists=centreService.getAllSpecialistsByCentre(user.getId());
        return ResponseEntity.status(200).body(specialists);

    }

    @GetMapping("/get-myCentre")
    public ResponseEntity getMyCentre(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.ok(centreService.getMyCentre(user.getId()));
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

    @GetMapping("get-all-old-bookings-by-centre")
    public ResponseEntity getAllOldBookingsByCentre(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(centreService.getAllOldBookingsByCentre(user.getId()));
    }

    @GetMapping("get-all-new-bookings-by-centre")
    public ResponseEntity getAllNewBookingsByCentre(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(centreService.getAllNewBookingsByCentre(user.getId()));
    }

    @GetMapping("/get-top5-center-by-avrRating")
    public ResponseEntity getTop5CenterByAvrRating(){
        return ResponseEntity.ok(centreService.getTop5CenterByAvrRating());
    }





}
