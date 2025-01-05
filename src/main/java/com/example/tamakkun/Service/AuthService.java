package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CentreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final CentreRepository centreRepository;

//    public void register(MyUser user) {
//        user.setRole("USER");
//        String HashPass = new BCryptPasswordEncoder().encode(user.getPassword());
//        user.setPassword(HashPass);
//        authRepository.save(user);
//    }

//    public List<MyUser> getAllUsers() {
//        return authRepository.findAll();
//    }

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

}
