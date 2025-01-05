package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.Model.Parent;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final ParentRepository parentRepository;

//    public void register(MyUser user) {
//        user.setRole("USER");
//        String HashPass = new BCryptPasswordEncoder().encode(user.getPassword());
//        user.setPassword(HashPass);
//        authRepository.save(user);
//    }

//    public List<MyUser> getAllUsers() {
//        return authRepository.findAll();
//    }
}
