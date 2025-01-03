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
//h

    @GetMapping("/get-my-children/{user_id}")
    public ResponseEntity getMyChildren (@PathVariable Integer user_id ){
        return ResponseEntity.status(200).body(childService.getMyChildren(user_id));
    }

    @GetMapping("/get-child/{user_id}/{child_id}")
    public ResponseEntity getChildById (@PathVariable Integer user_id , @PathVariable Integer child_id){
        return ResponseEntity.status(200).body(childService.getChildById(user_id,child_id));
    }

    @PostMapping("/add-child/{user_id}")
    public ResponseEntity addChild (@PathVariable Integer user_id , @RequestBody @Valid Child child){
        childService.addChild(user_id, child);
        return ResponseEntity.status(200).body(new ApiResponse("Added successfully"));
    }

    @PutMapping("/update/{user_id}/{child_id}")
    public ResponseEntity update (@PathVariable Integer user_id ,@PathVariable Integer child_id, @RequestBody @Valid Child child){
        childService.update(user_id, child_id,child);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete/{user_id}/{child_id}")
    public ResponseEntity delete (@PathVariable Integer user_id  ,@PathVariable Integer child_id){
        childService.delete(user_id, child_id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }

}
