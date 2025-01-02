package com.example.tamakkun.Service;

import com.example.tamakkun.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

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
