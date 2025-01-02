package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.Comment;
import com.example.tamakkun.Model.Community;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tamakkun-system/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/get-comments-By-Post/{post_id}")
    public ResponseEntity getCommentsByPost (@PathVariable Integer post_id){
        return ResponseEntity.status(200).body(commentService.getCommentsByPost(post_id));
    }

    @PostMapping("/add-comment/{post_id}")
    public ResponseEntity addComment (@AuthenticationPrincipal MyUser user ,@PathVariable Integer post_id, @RequestBody @Valid Comment comment){
        commentService.addComment(user.getId(), post_id,comment);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }

    @PutMapping("/update/{community_id}")
    public ResponseEntity update (@AuthenticationPrincipal MyUser myUser ,@PathVariable Integer post_id,@PathVariable  Integer comment_id, @RequestBody @Valid Comment comment){
        commentService.update(myUser.getId(),post_id,comment_id,comment);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete/{comment_id}")
    public ResponseEntity delete (@AuthenticationPrincipal MyUser myUser , @PathVariable Integer comment_id){
        commentService.delete(myUser.getId(),comment_id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }
}
