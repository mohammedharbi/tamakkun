package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_Out.SpecialistDTO_Out;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Specialist;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CentreRepository;
import com.example.tamakkun.Repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialistService {


    private final SpecialistRepository specialistRepository;
    private final AuthRepository authRepository;
    private final CentreRepository centreRepository;


    public List<SpecialistDTO_Out> getAllSpecialists(){
        List<Specialist> specialists = specialistRepository.findAll();
        List<SpecialistDTO_Out>specialistDTOOuts= new ArrayList<>();

        for(Specialist specialist : specialists){
            SpecialistDTO_Out specialistDTOOut = new SpecialistDTO_Out(specialist.getName(), specialist.getSpecialization(), specialist.getExperienceYears(),specialist.getImageUrl(), specialist.getSupportedDisabilities());
            specialistDTOOuts.add(specialistDTOOut);
        }
        return specialistDTOOuts;
    }


    //by centre
    public void addSpecialist(Integer centre_id, Specialist specialist){
        MyUser user =authRepository.findMyUserById(centre_id);

        if(user==null)
            throw new ApiException("Centre not found!");
        specialist.setCentre(user.getCentre());
        specialistRepository.save(specialist);
    }

    //by centre
    public void updateSpecialist(Integer specialist_id, Specialist newSpecialist, Integer centre_id) {

        Specialist oldSpecialist = specialistRepository.findById(specialist_id).orElse(null);
        if (oldSpecialist == null) {
            throw new ApiException("Specialist not found!");
        }

        Centre centre = centreRepository.findCentreById(centre_id);
        if (centre == null) {
            throw new ApiException("Centre not found!");
        }

        if (!oldSpecialist.getCentre().getId().equals(centre_id)) {
            throw new ApiException("This specialist does not belong to the specified centre!");
        }

        // update  specialist's details
        oldSpecialist.setName(newSpecialist.getName());
        oldSpecialist.setSpecialization(newSpecialist.getSpecialization());
        oldSpecialist.setExperienceYears(newSpecialist.getExperienceYears());
        oldSpecialist.setImageUrl(newSpecialist.getImageUrl());
        oldSpecialist.setSupportedDisabilities(newSpecialist.getSupportedDisabilities());

        oldSpecialist.setCentre(centre);

        // save info
        specialistRepository.save(oldSpecialist);
    }

    public void deleteSpecialist(Integer specialist_id, Integer centre_id){

        MyUser user=authRepository.findMyUserById(centre_id);
        if(user==null)
            throw new ApiException("Centre not found!");

        Specialist  specialist = specialistRepository.findSpecialistById(specialist_id);

        if(specialist==null)
            throw new ApiException("Specialist not found!");
        specialistRepository.delete(specialist);
    }

    //End CRUD

    public List<Specialist> getSpecialistsBySupportedDisability(String disabilityType) {
        List<Specialist> specialists = specialistRepository.findBySupportedDisabilitiesContaining(disabilityType);
        if (specialists.isEmpty()) {
            throw new ApiException("No specialists found for the given disability type!");
        }
        return specialists;
    }


    //2: Durrah
    public List<Specialist> getTopRatedSpecialists() {
        List<Specialist> results = specialistRepository.findTopRatedSpecialistsWithRatings();

        return results.stream()
                .limit(5) // Fetch top 5 specialists
                .map(result ->  results.get(0))
                .collect(Collectors.toList());
    }














}
