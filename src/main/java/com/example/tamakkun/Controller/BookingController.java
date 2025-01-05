package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.Booking;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Parent;
import com.example.tamakkun.Service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tamakkun-system/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/new-booking/parent/{parent_id}/child/{child_id}/centre/{centre_id}/hours/{hours}")
    public ResponseEntity newBooking(@AuthenticationPrincipal MyUser user, @PathVariable Integer child_id, @PathVariable Integer centre_id, @PathVariable Integer hours, @RequestBody @Valid Booking booking) {

        bookingService.newBooking(parent_id, child_id,centre_id,hours,booking);
        return ResponseEntity.status(200).body(new ApiResponse("Booked done and QR code generated successfully!"));

    }
    @PutMapping("/mark-scanned/{bookingId}")
    public ResponseEntity markBookingAsScanned(@AuthenticationPrincipal MyUser user, @PathVariable Integer bookingId){
        bookingService.markBookingAsScanned(user.getId(), bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Booking mark as scanned and completed successfully!"));

    }

//    @PostMapping("/send-reminder")
//    public ResponseEntity<ApiResponse> sendBookingReminders() {
//        String resultMessage = bookingService.sendBookingReminders();
//
//        // only return success message if reminders were actually sent s
//        return ResponseEntity.ok(new ApiResponse(resultMessage));
//    }

}
