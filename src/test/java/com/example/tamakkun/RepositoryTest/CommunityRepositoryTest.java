package com.example.tamakkun.RepositoryTest;


import com.example.tamakkun.Model.Community;
import com.example.tamakkun.Repository.CommunityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommunityRepositoryTest {

    @Autowired
    private CommunityRepository communityRepository;

    private Community community1, community2;

    @BeforeEach
    public void setUp() {
        community1 = new Community(null, "Community 1", "Description of community 1","1- Don't do this. 2- Don't do that",null,null,null);
        community2 = new Community(null, "Community 2", "Description of community 2","1- Don't do this. 2- Don't do that",null,null,null);

        communityRepository.save(community1);
        communityRepository.save(community2);
    }

    @Test
    public void testFindCommunityById() {
        Community foundCommunity = communityRepository.findCommunityById(community1.getId());
        assertThat(foundCommunity).isNotNull();
        assertThat(foundCommunity.getName()).isEqualTo("Community 1");
    }

    @Test
    public void testFindCommunityByIdNotFound() {
        Community foundCommunity = communityRepository.findCommunityById(999);  // Assuming 999 does not exist
        assertThat(foundCommunity).isNull();
    }

}