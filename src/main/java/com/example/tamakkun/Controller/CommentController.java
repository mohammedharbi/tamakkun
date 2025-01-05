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

    @PostMapping("/add-comment/{user_id}/{post_id}")
    public ResponseEntity addComment (@PathVariable Integer user_id ,@PathVariable Integer post_id, @RequestBody @Valid Comment comment){
        commentService.addComment(user_id, post_id,comment);
        return ResponseEntity.status(200).body(new ApiResponse("Comment added successfully!"));
    }

    @PutMapping("/update/{user_id}/{post_id}/{comment_id}")
    public ResponseEntity update (@PathVariable Integer user_id ,@PathVariable Integer post_id,@PathVariable  Integer comment_id, @RequestBody @Valid Comment comment){
        commentService.update(user_id,post_id,comment_id,comment);
        return ResponseEntity.status(200).body(new ApiResponse("comment updated successfully!"));
    }

    @DeleteMapping("/delete/{user_id}/{comment_id}")
    public ResponseEntity delete (@PathVariable Integer user_id , @PathVariable Integer comment_id){
        commentService.delete(user_id,comment_id);
        return ResponseEntity.status(200).body(new ApiResponse("Comment deleted successfully!"));
    }

    @GetMapping("/get-new-comments-By-Post/{post_id}")
    public ResponseEntity getNewCommentByPost (@PathVariable Integer post_id){
        return ResponseEntity.status(200).body(commentService.getNewCommentByPost(post_id));
    }
}
