package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_In.BookingDTO_In;
import com.example.tamakkun.Model.Booking;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Parent;
import com.example.tamakkun.Service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/tamakkun-system/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/new-booking/child/{child_id}/centre/{centre_id}")
    public ResponseEntity newBooking(@AuthenticationPrincipal MyUser user, @PathVariable Integer child_id, @PathVariable Integer centre_id, @RequestBody @Valid BookingDTO_In bookingDTOIn) {

        bookingService.newBooking(user.getId(), child_id,centre_id, bookingDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Booking confirmed! You will receive an email with the QR code and details."));

    }
    @PutMapping("/mark-scanned/{bookingId}")
    public ResponseEntity markBookingAsScanned(@AuthenticationPrincipal MyUser user, @PathVariable Integer bookingId){
        bookingService.markBookingAsScanned(user.getId(), bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Booking mark as scanned and completed successfully!"));

    }






}
