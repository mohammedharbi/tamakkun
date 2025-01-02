package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Post;
import com.example.tamakkun.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tamakkun-system/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/get-Post")
    public ResponseEntity getPosts (){
        return ResponseEntity.status(200).body(postService.getPosts());
    }

    @PostMapping("/add-post/{community_id}")
    public ResponseEntity addPost (@AuthenticationPrincipal MyUser user , @PathVariable Integer community_id, @RequestBody @Valid Post post){
        postService.addPost(user.getId(), community_id,post);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }

    @PutMapping("/update/{post_id}")
    public ResponseEntity update (@AuthenticationPrincipal MyUser myUser ,@PathVariable Integer post_id, @RequestBody @Valid Post post){
        postService.update(myUser.getId(),post_id,post);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }


    @DeleteMapping("/delete/{post_id}")
    public ResponseEntity deletePost (@AuthenticationPrincipal MyUser myUser , @PathVariable Integer post_id){
        postService.deletePost(myUser.getId(),post_id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }

    @GetMapping("/get-Post-by-id/{post_id}")
    public ResponseEntity getPostById (@PathVariable Integer  post_id){
        return ResponseEntity.status(200).body(postService.getPostById(post_id));
    }
}
