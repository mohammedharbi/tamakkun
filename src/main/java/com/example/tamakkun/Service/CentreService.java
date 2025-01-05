package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_In.CentreDTO_In;
import com.example.tamakkun.DTO_Out.ActivityDTO_Out;
import com.example.tamakkun.DTO_Out.CentreDTO_Out;
import com.example.tamakkun.DTO_Out.SpecialistDTO_Out;
import com.example.tamakkun.Model.Activity;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Specialist;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CentreRepository;
import com.example.tamakkun.Repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CentreService {

    private final CentreRepository centreRepository;
    private final AuthRepository authRepository;
    private final SpecialistRepository specialistRepository;

    //is verified must appear to admin? i think return Centre not just DTO

    //For admin
    public List<Centre> getAllCentres(){
      return centreRepository.findAll();
    }


    public void centreRegister(CentreDTO_In centreDTOIn) {

        if (centreDTOIn.getOpeningHour().isAfter(centreDTOIn.getClosingHour()) ||
                centreDTOIn.getOpeningHour().equals(centreDTOIn.getClosingHour())) {
            throw new ApiException("Opening hour must be before closing hour!");
        }


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

    //by centre itself? or admin?
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


    //3: Durrah

    public List<CentreDTO_Out> filterCentresByPrice(Double minPrice, Double maxPrice) {
        return centreRepository.findAll().stream()
                .filter(centre -> centre.getPricePerHour() >= minPrice && centre.getPricePerHour() <= maxPrice)
                .map(centre -> new CentreDTO_Out(
                        centre.getName(),
                        centre.getDescription(),
                        centre.getAddress(),
                        centre.getOpeningHour(),
                        centre.getClosingHour(),
                        centre.getPricePerHour(),
                        centre.getImageUrl()
                ))
                .collect(Collectors.toList());
    }

    public CentreDTO_Out getCentreByName(String name) {
        // use the repository method to fetch centre
        Centre centre = centreRepository.findCentreByName(name);

        if (centre == null) {
            throw new ApiException("Centre with name " + name + " not found!");
        }

        return new CentreDTO_Out(
                centre.getName(),
                centre.getDescription(),
                centre.getAddress(),
                centre.getOpeningHour(),
                centre.getClosingHour(),
                centre.getPricePerHour(),
                centre.getImageUrl()
        );
    }

    public List<CentreDTO_Out> getCentresByAddress(String address) {
        // retrive centres by address
        List<Centre> centres = centreRepository.findByAddressContainingIgnoreCase(address);

        // convert it to DTO
        return centres.stream()
                .map(centre -> new CentreDTO_Out(
                        centre.getName(),
                        centre.getDescription(),
                        centre.getAddress(),
                        centre.getOpeningHour(),
                        centre.getClosingHour(),
                        centre.getPricePerHour(),
                        centre.getImageUrl()))
                .collect(Collectors.toList());
    }

    public List<CentreDTO_Out> getCentresByHoursRange(LocalTime startOpening, LocalTime endClosing) {
       // specified time range
        List<Centre> centres = centreRepository.findCentresByOpeningAndClosingHourRange(startOpening, endClosing);

        return centres.stream()
                .map(centre -> new CentreDTO_Out(
                        centre.getName(),
                        centre.getDescription(),
                        centre.getAddress(),
                        centre.getOpeningHour(),
                        centre.getClosingHour(),
                        centre.getPricePerHour(),
                        centre.getImageUrl()))
                .collect(Collectors.toList());
    }


    public List<ActivityDTO_Out> getActivitiesByCentre(Integer centreId) {
        List<Activity> activities = centreRepository.findActivitiesByCentre(centreId);

        return activities.stream().map(activity -> new ActivityDTO_Out(
                activity.getName(),
                activity.getDescription(),
                activity.getAllowedDisabilities()
        )).collect(Collectors.toList());
    }


    public List<SpecialistDTO_Out> getSpecialistsByCentre(Integer centreId) {
        Centre centre = centreRepository.findById(centreId)
                .orElseThrow(() -> new ApiException("Centre not found!"));

        Set<Specialist> specialists = centre.getSpecialists();

        return specialists.stream()
                .map(specialist -> new SpecialistDTO_Out(
                        specialist.getName(),
                        specialist.getSpecialization(),
                        specialist.getExperienceYears(),
                        specialist.getImageUrl(),
                        specialist.getSupportedDisabilities()))
                .collect(Collectors.toList());
    }

    public Centre getMyCentre(Integer centreId) {
        return centreRepository.findCentreById(centreId);
    }










    }
