package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Parent;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CentreRepository;
import com.example.tamakkun.Repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final CentreRepository centreRepository;
    private final ParentRepository parentRepository;

    public List<MyUser> getAllUsers() {
        return authRepository.findAll();
    }

    public void verifyCentre(Integer centreId) {
        Centre centre = centreRepository.findCentreById(centreId);
        if(centre==null)
               throw  new ApiException("Centre with the given ID not found!");

        // check if commercial license is valid
        if (centre.getCommercialLicense() == null || centre.getCommercialLicense().isEmpty()) {
            throw new ApiException("Centre does not have a valid commercial license!");
        }

        // check if not already verified
        if (Boolean.TRUE.equals(centre.getIsVerified())) {
            throw new ApiException("Centre is already verified!");
        }

        centre.setIsVerified(true);
        centreRepository.save(centre);
    }

    public List<Centre> getAllUnverifiedCentres() {
        return centreRepository.findUnverifiedCentres(); // calling the @Query method
    }

    //E:#10 Mohammed
    public void unActiveParent(Integer parent_id){ // by admin
        Parent parent = parentRepository.findParentById(parent_id);
        if (parent == null) {throw new ApiException("parent not found");}
        if (parent.getIsActive()){
            parent.setIsActive(false);
        }else throw new ApiException("parent is already not active");
    }

    //E:#11 Mohammed
    public List<Parent> getAllUnActiveParent(){ // by admin
        List<Parent> unActiveParents = parentRepository.getParentByIsActive(false);

        if (unActiveParents.isEmpty()) {throw new ApiException("Not found any unActive parents");}

        return unActiveParents;
    }

}
