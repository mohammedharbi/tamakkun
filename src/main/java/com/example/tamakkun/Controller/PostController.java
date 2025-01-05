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

    @PostMapping("/add-post")
    public ResponseEntity addPost (@AuthenticationPrincipal MyUser user, @RequestBody @Valid Post post){
        postService.addPost(user.getId(), post);
        return ResponseEntity.status(200).body(new ApiResponse("Post added successfully!"));
    }

    @PutMapping("/update/{post_id}")
    public ResponseEntity update (@AuthenticationPrincipal MyUser user,@PathVariable Integer post_id, @RequestBody @Valid Post post){
        postService.update(user.getId(), post_id,post);
        return ResponseEntity.status(200).body(new ApiResponse("Post updated successfully!"));
    }


    @DeleteMapping("/delete/{post_id}")
    public ResponseEntity deletePost (@AuthenticationPrincipal MyUser user, @PathVariable Integer post_id){
        postService.deletePost(user.getId(), post_id);
        return ResponseEntity.status(200).body(new ApiResponse("Post deleted successfully!"));
    }

    @GetMapping("/get-Post-by-id/{post_id}")
    public ResponseEntity getPostById (@AuthenticationPrincipal MyUser user,@PathVariable Integer  post_id){
        return ResponseEntity.status(200).body(postService.getPostById(user.getId(), post_id));
    }

    @GetMapping("/search-by-keyword/{keyword}")
    public ResponseEntity searchByKeyword (@PathVariable String keyword){
        return ResponseEntity.status(200).body(postService.searchByKeyword(keyword));
    }

    @GetMapping("/search-by-date/{startDate}/{endDate}")
    public ResponseEntity searchByDate (@PathVariable String startDate,@PathVariable String endDate){
        return ResponseEntity.status(200).body(postService.searchByDate(startDate,endDate));
    }

    @PutMapping("like-post/{post_id}")
    public ResponseEntity likePost (@AuthenticationPrincipal MyUser user,@PathVariable Integer post_id){
        postService.likePost(user.getId(), post_id);
        return ResponseEntity.status(200).body(new ApiResponse("Post liked successfully!"));
    }

    @PutMapping("unlike-post/{post_id}")
    public ResponseEntity unlikePost (@AuthenticationPrincipal MyUser user ,@PathVariable Integer post_id){
        postService.unlikePost(user.getId(), post_id);
        return ResponseEntity.status(200).body(new ApiResponse("Post unliked successfully!"));
    }

    @GetMapping("/get-all-liked-posts")
    public ResponseEntity getAllLikedPosts (@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(postService.getAllLikedPosts(user.getId()));
    }

    @PostMapping("/bookmark-post/{post_id}")
    public ResponseEntity bookmarkPost (@AuthenticationPrincipal MyUser user, @PathVariable Integer post_id){
        postService.bookmarkPost(user.getId(), post_id);
        return ResponseEntity.status(200).body(new ApiResponse("Post bookmarked successfully!"));
    }

    @DeleteMapping("/remove-bookmark-post/{post_id}")
    public ResponseEntity removeBookmark (@AuthenticationPrincipal MyUser user, @PathVariable Integer post_id){
        postService.removeBookmark(user.getId(), post_id);
        return ResponseEntity.status(200).body(new ApiResponse("Post remove from bookmark successfully!"));
    }

    @GetMapping("/get-bookmark-posts")
    public ResponseEntity getBookmarkedPost (@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(postService.getBookmarkedPost(user.getId()));
    }

    @GetMapping("/get-post-by-parent-name/{parentName}")
    public ResponseEntity getAllPostByParentName (@PathVariable String  parentName){
        return ResponseEntity.status(200).body(postService.getAllPostByParentName(parentName));
    }
}
