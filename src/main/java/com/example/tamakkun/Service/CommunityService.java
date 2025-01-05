package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_Out.CommunityDTO_out;
import com.example.tamakkun.Model.Community;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final AuthRepository authRepository;

    public CommunityDTO_out getCommunity (Integer user_id, Integer community_id){
        MyUser user=authRepository.findMyUserById(user_id);
        if(user==null)
            throw new ApiException("User not found!");

        Community community =communityRepository.findCommunityById(community_id);
        if (community==null){
            throw new ApiException("Community not found");}
        return new  CommunityDTO_out(community.getName(),community.getDescription(),community.getRules(),community.getCreatedAt());
    }



    public void update (Integer user_id , Integer community_id , Community community){
        MyUser user= authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user id not found");}
        Community old =communityRepository.findCommunityById(community_id);
        if (old==null){
            throw new ApiException("Community not found");}
        old.setName(community.getName());
        old.setDescription(community.getDescription());
        old.setRules(community.getRules());
        communityRepository.save(old);
    }

    public void delete (Integer user_id , Integer community_id){
        MyUser user= authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user id not found");}
        Community community =communityRepository.findCommunityById(community_id);
        if (community==null){
            throw new ApiException("Community not found");}
        communityRepository.delete(community);
    }

}
