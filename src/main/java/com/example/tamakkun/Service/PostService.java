package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_Out.ChildDTO_Out;
import com.example.tamakkun.DTO_Out.PostDTO_Out;
import com.example.tamakkun.Model.Child;
import com.example.tamakkun.Model.Community;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Post;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CommunityRepository;
import com.example.tamakkun.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AuthRepository authRepository;
    private final CommunityRepository communityRepository;


    public List<PostDTO_Out> getPosts (){
        return convertPostToDTO(postRepository.findAll());
    }


    public void addPost (Integer user_id , Integer community_id, Post post){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Community community= communityRepository.findCommunityById(community_id);
        if (community==null){
            throw new ApiException("Community not found");}
        post.setCreatedAt(LocalDateTime.now());
        post.setCommunity(community);
        post.setParent(user.getParent());
        postRepository.save(post);
    }

    public void update (Integer user_id , Integer post_id, Post post){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Post old = postRepository.findPostById(post_id);
        if (old==null){
            throw new ApiException("post not found");}
        old.setTitle(post.getTitle());
        old.setContent(post.getContent());
        postRepository.save(old);
    }


    public void deletePost (Integer user_id , Integer post_id){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Post post =postRepository.findPostById(post_id);
        if (post==null){
            throw new ApiException("post not found");}
        if (!post.getParent().getId().equals(user.getId())){
            throw new ApiException("you not allowed to delete this post");}
        postRepository.delete(post);
    }


    //#
    public PostDTO_Out getPostById (Integer post_id){
        Post post =postRepository.findPostById(post_id);
        if (post==null){
            throw new ApiException("post not found");}
        return new PostDTO_Out(post.getTitle(), post.getContent(), post.getCreatedAt(),post.getParent().getFullName());
    }

    public List<PostDTO_Out> convertPostToDTO(Collection<Post> posts){
        List<PostDTO_Out> postDTOOuts = new ArrayList<>();
        for(Post p : posts){
            postDTOOuts.add(new PostDTO_Out(p.getTitle(),p.getContent(),p.getCreatedAt(),p.getParent().getFullName()));}
        return postDTOOuts;
    }


}
