package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tamakkun-system/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PutMapping("/centre-verify/{centreId}")
    public ResponseEntity verifyCentre(@PathVariable Integer centreId) {
        authService.verifyCentre(centreId);
        return ResponseEntity.ok(new ApiResponse("Centre has been successfully verified!"));
    }

    @GetMapping("/get-unverified-centres")
    public ResponseEntity getAllUnverifiedCentres(){
        return ResponseEntity.ok(authService.getAllUnverifiedCentres());
    }


}
