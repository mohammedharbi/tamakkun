package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.PostComment;
import com.example.tamakkun.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<PostComment,Integer> {
    PostComment findCommentById (Integer id);

    List<PostComment> findAllByPost (Post post);

    @Query("select c from PostComment c where c.post.id=?1 order by c.createdAt desc ")
    List<PostComment> findAllByNewCommentByPost (Integer post_id);
}
