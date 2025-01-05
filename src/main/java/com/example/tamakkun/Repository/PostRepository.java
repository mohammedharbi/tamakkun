package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Community;
import com.example.tamakkun.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    Post findPostById (Integer id);
    List<Post> findByTitleContainingOrContentContaining (String titleKeyword,String contentKeyword );
   @Query("select p from Post p where p.createdAt between :startDate and :endDate")
    List<Post> findByCreatedAtBetween (LocalDate startDate , LocalDate endDate);
    List<Post> findAllByLikedByContains (Integer parent_id);

    @Query("select p from Post p where p.parent.fullName=?1")
    List<Post> findAllByParentName (String name);


}
