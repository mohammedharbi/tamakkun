package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_In.CentreDTO_In;
import com.example.tamakkun.DTO_Out.CentreDTO_Out;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CentreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class CentreService {

    private final CentreRepository centreRepository;
    private final AuthRepository authRepository;

    public List<CentreDTO_Out> getAllCentres(){
        List<Centre> centres = centreRepository.findAll();
        List<CentreDTO_Out> centreDTO_outs= new ArrayList<>();

        for(Centre centre : centres){
            CentreDTO_Out centreDTOOut = new CentreDTO_Out(centre.getName(), centre.getDescription(), centre.getAddress(), centre.getOpeningHour(), centre.getClosingHour(), centre.getIsVerified(), centre.getPricePerHour(), centre.getImageUrl());
            centreDTO_outs.add(centreDTOOut);
        }
        return centreDTO_outs;
    }


    public void centreRegister(CentreDTO_In centreDTOIn) {

        MyUser myUser = new MyUser();

        myUser.setUsername(centreDTOIn.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(centreDTOIn.getPassword());
        myUser.setPassword(hashPassword);
        myUser.setEmail(centreDTOIn.getEmail());

        myUser.setRole("CENTRE");

        Centre centre = new Centre();

        centre.setName(centreDTOIn.getName());
        centre.setDescription(centreDTOIn.getDescription());
        centre.setAddress(centreDTOIn.getAddress());
        centre.setOpeningHour(centreDTOIn.getOpeningHour());
        centre.setClosingHour(centreDTOIn.getClosingHour());
        centre.setCommercialLicense(centreDTOIn.getCommercialLicense());
        centre.setPhoneNumber(centreDTOIn.getPhoneNumber());
        centre.setPricePerHour(centreDTOIn.getPricePerHour());
        centre.setImageUrl(centreDTOIn.getImageUrl());

        centre.setMyUser(myUser);

        //save user and centre

        authRepository.save(myUser);
        centreRepository.save(centre);

    }


    public void updateCentre(Integer centre_id, CentreDTO_In centreDTOIn, Integer user_id){

        Centre oldCentre = centreRepository.findCentreById(centre_id);
        if(oldCentre==null)
            throw new ApiException("Centre not found!");

        MyUser oldUser = authRepository.findMyUserById(oldCentre.getMyUser().getId());
        if(oldUser==null)
            throw new ApiException("User not found!");

        if(oldUser.getId() != user_id)
            throw new ApiException("You cannot update this centre!");

        oldUser.setUsername(centreDTOIn.getUsername());
        oldUser.setEmail(centreDTOIn.getEmail());
        oldUser.setPassword(new BCryptPasswordEncoder().encode(centreDTOIn.getPassword()));

        oldCentre.setName(centreDTOIn.getName());
        oldCentre.setAddress(centreDTOIn.getAddress());
        oldCentre.setDescription(centreDTOIn.getDescription());
        oldCentre.setPhoneNumber(centreDTOIn.getPhoneNumber());
        oldCentre.setOpeningHour(centreDTOIn.getOpeningHour());
        oldCentre.setClosingHour(centreDTOIn.getClosingHour());
        oldCentre.setPricePerHour(centreDTOIn.getPricePerHour());
        oldCentre.setImageUrl(centreDTOIn.getImageUrl());

        authRepository.save(oldUser);
        centreRepository.save(oldCentre);


    }

    //by centre itself
    public void deleteCentre(Integer user_id){

        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("User not found!");
        }

        Centre centre= centreRepository.findCentreById(user_id);
        if(centre==null)
            throw new ApiException("Center not found!");

        authRepository.delete(user);

    }













}
