package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tamakkun-system/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/get-all-users")
    public ResponseEntity getAllUsers(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(authService.getAllUsers());
    }

    @PutMapping("/centre-verify/{centreId}")
    public ResponseEntity verifyCentre(@AuthenticationPrincipal MyUser user, @PathVariable Integer centreId) {
        authService.verifyCentre(centreId);
        return ResponseEntity.ok(new ApiResponse("Centre has been successfully verified!"));
    }

    @GetMapping("/get-unverified-centres")
    public ResponseEntity getAllUnverifiedCentres(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.ok(authService.getAllUnverifiedCentres());
    }

    @PutMapping("/un-active-parent/{parent_id}")// by admin
    public ResponseEntity unActiveParent(@AuthenticationPrincipal MyUser user, @PathVariable Integer parent_id){
        authService.unActiveParent(parent_id);
        return ResponseEntity.ok(new ApiResponse("Parent activity status changed to un-active!"));
    }

    @GetMapping("/get-all-un-active-parent")
    public ResponseEntity getAllUnActiveParent(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(authService.getAllUnActiveParent());
    }


}
