package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_Out.ChildDTO_Out;
import com.example.tamakkun.DTO_Out.PostDTO_Out;
import com.example.tamakkun.Model.*;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CommunityRepository;
import com.example.tamakkun.Repository.ParentRepository;
import com.example.tamakkun.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AuthRepository authRepository;
    private final CommunityRepository communityRepository;
    private final ParentRepository parentRepository;



    public List<PostDTO_Out> getPosts (){
        return convertPostToDTO(postRepository.findAll());
    }


    public void addPost (Integer user_id ,  Post post){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        if (!user.getParent().getIsActive()){throw new ApiException("Parent is not active therefore is not permitted to access this service!");}

        Community community= communityRepository.findCommunityById(1);
        if (community==null){
            throw new ApiException("Community not found");}
        post.setCreatedAt(LocalDate.now());
        post.setCommunity(community);
        post.setParent(user.getParent());
        postRepository.save(post);
    }

    public void update (Integer user_id , Integer post_id, Post post){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        if (!user.getParent().getIsActive()){throw new ApiException("Parent is not active therefore is not permitted to access this service!");}

        Post old = postRepository.findPostById(post_id);

        if (old==null){
            throw new ApiException("post not found");}
        if (!old.getParent().getId().equals(user.getId())){
            throw new ApiException("Parent is not permitted to access this service!");
        }
        old.setTitle(post.getTitle());
        old.setContent(post.getContent());
        postRepository.save(old);
    }


    public void deletePost (Integer user_id , Integer post_id){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        if (!user.getParent().getIsActive()){throw new ApiException("Parent is not active therefore is not permitted to access this service!");}

        Post post =postRepository.findPostById(post_id);
        if (post==null){
            throw new ApiException("post not found");}
        if (!post.getParent().getId().equals(user.getId())){
            throw new ApiException("you not allowed to delete this post");}
        postRepository.delete(post);
    }


    //#
    public PostDTO_Out getPostById ( Integer user_id,Integer post_id){
        MyUser user= authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Post post =postRepository.findPostById(post_id);
        if (post==null){
            throw new ApiException("post not found");}

        return new PostDTO_Out(post.getTitle(), post.getContent(), post.getCreatedAt(),post.getParent().getFullName(),post.getLikes());
    }


    public List<PostDTO_Out> searchByKeyword (String keyword){
        return
                convertPostToDTO(postRepository.findByTitleContainingOrContentContaining(keyword,keyword));}



    public List<PostDTO_Out> searchByDate (String startDate, String endDate){
        LocalDate start =LocalDate.parse(startDate);
        LocalDate end =LocalDate.parse(endDate);
        return convertPostToDTO(postRepository.findByCreatedAtBetween(start,end));
    }


    public void likePost (Integer user_id , Integer post_id){
        MyUser user = authRepository.findMyUserById(user_id);
        if(user==null){
            throw new ApiException("user not found");}
        if (!user.getParent().getIsActive()){throw new ApiException("Parent is not active therefore is not permitted to access this service!");}
        Post post= postRepository.findPostById(post_id);
        if(post==null){
            throw new ApiException("post not found");}
        //check if the user has already liked this post
        if(post.getLikedBy().contains(user_id)){
            throw new ApiException("you already liked this post");}

        post.getLikedBy().add(user_id);
        post.setLikes(post.getLikes() +1);
        postRepository.save(post);
    }


    public void unlikePost (Integer user_id , Integer post_id) {
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");}
        if (!user.getParent().getIsActive()){throw new ApiException("Parent is not active therefore is not permitted to access this service!");}
        Post post = postRepository.findPostById(post_id);
        if (post == null) {
            throw new ApiException("post not found");
        }
        //check if the user has liked this post
        if(!post.getLikedBy().contains(user_id)){
            throw new ApiException("you have not liked this post");}
        post.getLikedBy().remove(user_id);
        post.setLikes(post.getLikes() -1);
        postRepository.save(post);

    }

public List<PostDTO_Out> getAllLikedPosts (Integer user_id){
    MyUser user= authRepository.findMyUserById(user_id);
    if (user == null) {
        throw new ApiException("user not found");}
    Parent parent= parentRepository.findParentByMyUser(user);
    if(parent ==null){
        throw new ApiException("parent not found");}
    return convertPostToDTO(postRepository.findAllByLikedByContains(parent.getId())) ;
}


    public void  bookmarkPost (Integer user_id , Integer post_id){
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");}
        if (!user.getParent().getIsActive()){throw new ApiException("Parent is not active therefore is not permitted to access this service!");}
        Post post = postRepository.findPostById(post_id);
        if (post == null) {
            throw new ApiException("post not found");}
        if (user.getParent().getBookmarkedPostIds().contains(post_id)){
            throw new ApiException("This post is already bookmarked");}
        user.getParent().getBookmarkedPostIds().add(post_id);
        parentRepository.save(user.getParent());
    }

    public void removeBookmark (Integer user_id , Integer post_id){
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");}
        if (!user.getParent().getIsActive()){throw new ApiException("Parent is not active therefore is not permitted to access this service!");}
        Post post = postRepository.findPostById(post_id);
        if (post == null) {
            throw new ApiException("post not found");}
        if (!user.getParent().getBookmarkedPostIds().contains(post_id)){
            throw new ApiException("This post is not in parent's bookmarks");}
        user.getParent().getBookmarkedPostIds().remove(post_id);
        parentRepository.save(user.getParent());
    }

    public List<PostDTO_Out> getBookmarkedPost (Integer user_id){
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");
        }
        Set<Integer> bookmarkedPostIds = user.getParent().getBookmarkedPostIds();
        if(bookmarkedPostIds==null){
            throw new ApiException("No bookmarked posts found");}
        return convertPostToDTO(postRepository.findAllById(bookmarkedPostIds));
    }


    public List<PostDTO_Out> getAllPostByParentName (String parentName){
        return convertPostToDTO(postRepository.findAllByParentName(parentName));
    }


    public List<PostDTO_Out> convertPostToDTO(Collection<Post> posts){
        List<PostDTO_Out> postDTOOuts = new ArrayList<>();
        for(Post p : posts){
            postDTOOuts.add(new PostDTO_Out(p.getTitle(),p.getContent(),p.getCreatedAt(),p.getParent().getFullName(),p.getLikes()));}
        return postDTOOuts;
    }


}
