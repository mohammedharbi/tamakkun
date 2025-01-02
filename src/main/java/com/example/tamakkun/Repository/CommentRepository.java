package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Comment;
import com.example.tamakkun.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Comment findCommentById (Integer id);

    List<Comment> findAllByPost (Post post);
}
