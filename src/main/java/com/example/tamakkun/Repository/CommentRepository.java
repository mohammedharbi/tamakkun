package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Comment;
import com.example.tamakkun.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Comment findCommentById (Integer id);

    List<Comment> findAllByPost (Post post);

    @Query("select c from Comment c where c.post.id=?1 order by c.createdAt desc ")
    List<Comment> findAllByNewCommentByPost (Integer post_id);
}
