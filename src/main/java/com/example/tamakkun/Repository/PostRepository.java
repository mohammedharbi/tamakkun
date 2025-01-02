package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Community;
import com.example.tamakkun.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    Post findPostById (Integer id);
    List<Post> findAllByCommunityId (Integer community_id);
}
