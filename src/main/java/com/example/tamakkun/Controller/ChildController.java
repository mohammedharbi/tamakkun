package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.Child;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Service.ChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tamakkun-system/child")
@RequiredArgsConstructor
public class ChildController {
    private final ChildService childService;


    @GetMapping("/get-my-children")
    public ResponseEntity getMyChildren (@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(childService.getMyChildren(user.getId()));
    }

    @GetMapping("/get-child/{child_id}")
    public ResponseEntity getChildById (@AuthenticationPrincipal MyUser user, @PathVariable Integer child_id){
        return ResponseEntity.status(200).body(childService.getChildById(user.getId(),child_id));
    }

    @PostMapping("/add-child/{user_id}")
    public ResponseEntity addChild (@PathVariable Integer user_id , @RequestBody @Valid Child child){
        childService.addChild(user_id, child);
        return ResponseEntity.status(200).body(new ApiResponse("Added successfully"));
    }

    @PutMapping("/update/{child_id}")
    public ResponseEntity update (@AuthenticationPrincipal MyUser myUser ,@PathVariable Integer child_id, @RequestBody @Valid Child child){
        childService.update(myUser.getId(), child_id,child);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete/{child_id}")
    public ResponseEntity delete (@AuthenticationPrincipal MyUser myUser ,@PathVariable Integer child_id){
        childService.delete(myUser.getId(), child_id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }

}
