package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Model.PostComment;
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
    public ResponseEntity addComment (@AuthenticationPrincipal MyUser user, @PathVariable Integer post_id, @RequestBody @Valid PostComment postComment){
        commentService.addComment(user.getId(), post_id, postComment);
        return ResponseEntity.status(200).body(new ApiResponse("PostComment added successfully!"));
    }

    @PutMapping("/update/{post_id}/{comment_id}")
    public ResponseEntity update (@AuthenticationPrincipal MyUser user,@PathVariable Integer post_id,@PathVariable  Integer comment_id, @RequestBody @Valid PostComment postComment){
        commentService.update(user.getId(), post_id,comment_id, postComment);
        return ResponseEntity.status(200).body(new ApiResponse("postComment updated successfully!"));
    }

    @DeleteMapping("/delete/{comment_id}")
    public ResponseEntity delete (@AuthenticationPrincipal MyUser user, @PathVariable Integer comment_id){
        commentService.delete(user.getId(), comment_id);
        return ResponseEntity.status(200).body(new ApiResponse("PostComment deleted successfully!"));
    }

    @GetMapping("/get-new-comments-By-Post/{post_id}")
    public ResponseEntity getNewCommentByPost (@PathVariable Integer post_id){
        return ResponseEntity.status(200).body(commentService.getNewCommentByPost(post_id));
    }
}
