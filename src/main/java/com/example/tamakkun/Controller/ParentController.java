package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_In.ParentDTO_In;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tamakkun-system/parent")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;


    @GetMapping("get-parent-by-id/{user_id}")
    public  ResponseEntity getParentById (@PathVariable Integer user_id){
        return ResponseEntity.status(200).body(parentService.getParentById(user_id));
    }

    @PostMapping("/parent-register")
    public ResponseEntity register (@RequestBody @Valid ParentDTO_In parentDTOIn){
        parentService.register(parentDTOIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Parent added successfully"));
    }
    @PutMapping("/update/{user_id}")
    public ResponseEntity update (@PathVariable Integer user_id, @RequestBody @Valid ParentDTO_In  parentDTOIn){
        parentService.update(user_id,parentDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }
    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity delete (@PathVariable Integer user_id ){
        parentService.delete(user_id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }

}
