package com.example.tamakkun.ServiceTest;

import com.example.tamakkun.DTO_Out.CommunityDTO_out;
import com.example.tamakkun.Model.Community;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CommunityRepository;
import com.example.tamakkun.Service.CommunityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class CommunityServiceTest {


    @InjectMocks
    CommunityService communityService;

    @Mock
    CommunityRepository communityRepository;

    @Mock
    AuthRepository authRepository;

    MyUser myUser;
    Community community1, community2;
    CommunityDTO_out community3;

    List<CommunityDTO_out> communities;

    @BeforeEach
    void setUp() {
        myUser = new MyUser(null, "Durra23", "124", "PARENT","durra@gmail.com", null, null, null, null);
        community1 = new Community(null, "Community1", "Description1", "Rules1", LocalDateTime.now(), null, null);
        community2 = new Community(null, "Community2", "Description2", "Rules2", LocalDateTime.now(), null, null);
        community3 = new CommunityDTO_out("comm1", "desc1", "rules2", LocalDateTime.now());

//        communities = new ArrayList<>();
//        communities.add(community3);
    }

    @Test
    public void getCommunitiesTest() {
        when(authRepository.findMyUserById(myUser.getId())).thenReturn(myUser);
        when(communityRepository.findCommunityById(community1.getId())).thenReturn(community1);

        CommunityDTO_out community= communityService.getCommunity(myUser.getId(), community1.getId());


        Mockito.verify(authRepository,times(1)).findMyUserById(myUser.getId());
        Mockito.verify(communityRepository, times(1)).findCommunityById(community1.getId());
    }

    @Test
    public void updateCommunityTest() {
        when(authRepository.findMyUserById(myUser.getId())).thenReturn(myUser);
        when(communityRepository.findCommunityById(community1.getId())).thenReturn(community1);

        communityService.update(myUser.getId(), community1.getId(), community1);

        verify(authRepository, times(1)).findMyUserById(myUser.getId());
        verify(communityRepository, times(1)).findCommunityById(community1.getId());
        verify(communityRepository, times(1)).save(community1);
    }

    @Test
    public void deleteCommunityTest() {
        when(authRepository.findMyUserById(myUser.getId())).thenReturn(myUser);
        when(communityRepository.findCommunityById(community1.getId())).thenReturn(community1);

        communityService.delete(myUser.getId(), community1.getId());

        verify(authRepository, times(1)).findMyUserById(myUser.getId());
        verify(communityRepository, times(1)).findCommunityById(community1.getId());
        verify(communityRepository, times(1)).delete(community1);
    }
}
