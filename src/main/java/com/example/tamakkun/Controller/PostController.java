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

    @PostMapping("/add-post/{user_id}")
    public ResponseEntity addPost (@PathVariable Integer user_id, @RequestBody @Valid Post post){
        postService.addPost(user_id,post);
        return ResponseEntity.status(200).body(new ApiResponse("Post added successfully!"));
    }

    @PutMapping("/update/{user_id}/{post_id}")
    public ResponseEntity update (@PathVariable Integer user_id,@PathVariable Integer post_id, @RequestBody @Valid Post post){
        postService.update(user_id,post_id,post);
        return ResponseEntity.status(200).body(new ApiResponse("Post updated successfully!"));
    }


    @DeleteMapping("/delete/{user_id}/{post_id}")
    public ResponseEntity deletePost (@PathVariable Integer user_id , @PathVariable Integer post_id){
        postService.deletePost(user_id,post_id);
        return ResponseEntity.status(200).body(new ApiResponse("Post deleted successfully!"));
    }

    @GetMapping("/get-Post-by-id/{post_id}")
    public ResponseEntity getPostById (@PathVariable Integer  post_id){
        return ResponseEntity.status(200).body(postService.getPostById(post_id));
    }

    @GetMapping("/search-by-keyword/{keyword}")
    public ResponseEntity searchByKeyword (@PathVariable String keyword){
        return ResponseEntity.status(200).body(postService.searchByKeyword(keyword));
    }

    @GetMapping("/search-by-date/{startDate}/{endDate}")
    public ResponseEntity searchByDate (@PathVariable String startDate,@PathVariable String endDate){
        return ResponseEntity.status(200).body(postService.searchByDate(startDate,endDate));
    }

    @PutMapping("like-post/{user_id}/{post_id}")
    public ResponseEntity likePost (@PathVariable Integer user_id ,@PathVariable Integer post_id){
        postService.likePost(user_id,post_id);
        return ResponseEntity.status(200).body(new ApiResponse("Post liked successfully!"));
    }

    @PutMapping("unlike-post/{user_id}/{post_id}")
    public ResponseEntity unlikePost (@PathVariable Integer user_id ,@PathVariable Integer post_id){
        postService.unlikePost(user_id,post_id);
        return ResponseEntity.status(200).body(new ApiResponse("Post unliked successfully!"));
    }

    @GetMapping("/get-all-liked-posts/{user_id}")
    public ResponseEntity getAllLikedPosts (@PathVariable Integer user_id){
        return ResponseEntity.status(200).body(postService.getAllLikedPosts(user_id));
    }

    @PostMapping("/bookmark-post/{user_id}/{post_id}")
    public ResponseEntity bookmarkPost (@PathVariable Integer user_id , @PathVariable Integer post_id){
        postService.bookmarkPost(user_id,post_id);
        return ResponseEntity.status(200).body(new ApiResponse("Post bookmarked successfully!"));
    }

    @DeleteMapping("/remove-bookmark-post/{user_id}/{post_id}")
    public ResponseEntity removeBookmark (@PathVariable Integer user_id , @PathVariable Integer post_id){
        postService.removeBookmark(user_id,post_id);
        return ResponseEntity.status(200).body(new ApiResponse("Post remove from bookmark successfully!"));
    }

    @GetMapping("/get-bookmark-posts/{user_id}")
    public ResponseEntity getBookmarkedPost (@PathVariable Integer user_id){
        return ResponseEntity.status(200).body(postService.getBookmarkedPost(user_id));
    }

    @GetMapping("/get-post-by-parent-name/{parentName}")
    public ResponseEntity getAllPostByParentName (@PathVariable String  parentName){
        return ResponseEntity.status(200).body(postService.getAllPostByParentName(parentName));
    }
}
