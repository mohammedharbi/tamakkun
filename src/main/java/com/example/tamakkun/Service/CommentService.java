package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_Out.CommentDTO_Out;
import com.example.tamakkun.Model.PostComment;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Post;
import com.example.tamakkun.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final AuthRepository authRepository;
    private final PostRepository postRepository;


    public List<CommentDTO_Out> getCommentsByPost (Integer post_id){
        Post post= postRepository.findPostById(post_id);
        if (post==null){
            throw new ApiException("post not found");}
        return convertCommentToDTO(commentRepository.findAllByPost(post));
    }

    public void addComment (Integer user_id ,Integer post_id, PostComment postComment){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        if (!user.getParent().getIsActive()){throw new ApiException("Parent is not active therefore is not permitted to access this service!");}
        Post post =postRepository.findPostById(post_id);
        if (post==null){
            throw new ApiException("post not found");}
        postComment.setPost(post);
        postComment.setParent(user.getParent());
        postComment.setCreatedAt(LocalDate.now());
        commentRepository.save(postComment);
    }


    public void update (Integer user_id , Integer post_id , Integer comment_id, PostComment postComment){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        if (!user.getParent().getIsActive()){throw new ApiException("Parent is not active therefore is not permitted to access this service!");}
        Post post =postRepository.findPostById(post_id);
        if (post==null){
            throw new ApiException("post not found");}
        PostComment old = commentRepository.findCommentById(comment_id);
        if (old==null){
            throw new ApiException("PostComment not found");}
        if(!old.getParent().getId().equals(user_id)){
            throw new ApiException("You not allow to update this postComment");}

            old.setContent(postComment.getContent());
        commentRepository.save(old);
    }


    public void delete (Integer user_id, Integer comment_id ){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        if (!user.getParent().getIsActive()){throw new ApiException("Parent is not active therefore is not permitted to access this service!");}
        PostComment postComment = commentRepository.findCommentById(comment_id);
        if (postComment ==null){
            throw new ApiException("PostComment not found");}
        if(!postComment.getParent().getId().equals(user_id)){
            throw new ApiException("You not allow to delete this postComment");}
        commentRepository.delete(postComment);
    }

    public List<CommentDTO_Out> getNewCommentByPost (Integer post_id){
        return convertCommentToDTO(commentRepository.findAllByNewCommentByPost(post_id)) ;
    }

    public List<CommentDTO_Out> convertCommentToDTO(Collection<PostComment> postComments){
        List<CommentDTO_Out> commentDTOOuts = new ArrayList<>();
        for(PostComment c : postComments){
            commentDTOOuts.add(new CommentDTO_Out(c.getContent(),c.getParent().getFullName(),c.getCreatedAt()));}
        return commentDTOOuts;
    }

}
