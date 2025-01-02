package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Integer> {
    Community findCommunityById (Integer id);

}
