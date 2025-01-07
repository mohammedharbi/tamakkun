package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.Community;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Service.CommunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tamakkun-system/community")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;


    @GetMapping("/get-community/{community_id}")
    public ResponseEntity getCommunity (@AuthenticationPrincipal MyUser user, @PathVariable Integer community_id){
        return ResponseEntity.status(200).body(communityService.getCommunity(user.getId(),community_id));
    }
    @PutMapping("/update/{community_id}")
    public ResponseEntity update (@AuthenticationPrincipal MyUser myUser ,@PathVariable Integer community_id, @RequestBody @Valid Community community){
        communityService.update(myUser.getId(), community_id,community);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete/{community_id}")
    public ResponseEntity delete (@AuthenticationPrincipal MyUser myUser , @PathVariable Integer community_id){
        communityService.delete(myUser.getId(),community_id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }

}
