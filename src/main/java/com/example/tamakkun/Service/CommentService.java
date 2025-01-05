package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_Out.CommentDTO_Out;
import com.example.tamakkun.DTO_Out.PostDTO_Out;
import com.example.tamakkun.Model.Comment;
import com.example.tamakkun.Model.Community;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Post;
import com.example.tamakkun.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void addComment (Integer user_id ,Integer post_id, Comment comment){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Post post =postRepository.findPostById(post_id);
        if (post==null){
            throw new ApiException("post not found");}
        comment.setPost(post);
        comment.setParent(user.getParent());
        comment.setCreatedAt(LocalDate.now());
        commentRepository.save(comment);
    }


    public void update (Integer user_id , Integer post_id , Integer comment_id, Comment comment){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Post post =postRepository.findPostById(post_id);
        if (post==null){
            throw new ApiException("post not found");}
        Comment old = commentRepository.findCommentById(comment_id);
        if (old==null){
            throw new ApiException("Comment not found");}
        old.setContent(comment.getContent());
        commentRepository.save(old);
    }


    public void delete (Integer user_id, Integer comment_id ){
        MyUser user =authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found");}
        Comment comment = commentRepository.findCommentById(comment_id);
        if (comment==null){
            throw new ApiException("Comment not found");}
        commentRepository.delete(comment);
    }

    public List<CommentDTO_Out> getNewCommentByPost (Integer post_id){
        return convertCommentToDTO(commentRepository.findAllByNewCommentByPost(post_id)) ;
    }

    public List<CommentDTO_Out> convertCommentToDTO(Collection<Comment> comments){
        List<CommentDTO_Out> commentDTOOuts = new ArrayList<>();
        for(Comment c : comments){
            commentDTOOuts.add(new CommentDTO_Out(c.getContent(),c.getParent().getFullName(),c.getCreatedAt()));}
        return commentDTOOuts;
    }

}
