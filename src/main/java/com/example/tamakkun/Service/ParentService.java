package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_In.ParentDTO_In;
import com.example.tamakkun.DTO_Out.ParentDTO_Out;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Parent;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;
    private final AuthRepository authRepository;

    //#
    public ParentDTO_Out getParentById (Integer user_id ){
        MyUser user = authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException(" user not found");
        }
        Parent parent= parentRepository.findParentByMyUser(user);
        return new ParentDTO_Out(parent.getFullName(), parent.getPhoneNumber(), parent.getAddress());
    }


    public void register (ParentDTO_In parentDTOIn){
        MyUser myUser =new MyUser();
        myUser.setUsername(parentDTOIn.getUsername());
        String hashPassword= new BCryptPasswordEncoder().encode(parentDTOIn.getPassword());
        myUser.setPassword(hashPassword);
        myUser.setRole("PARENT");
        myUser.setEmail(parentDTOIn.getEmail());
        authRepository.save(myUser);
        Parent parent=new Parent();
        parent.setFullName(parentDTOIn.getFullName());
        parent.setPhoneNumber(parentDTOIn.getPhoneNumber());
        parent.setAddress(parentDTOIn.getAddress());
        parent.setMyUser(myUser);
        parentRepository.save(parent);
    }

    public void update (Integer user_id , ParentDTO_In parentDTOIn){
        MyUser myUser= authRepository.findMyUserById(user_id);
        if (myUser==null){
            throw new ApiException("wrong user id");
        }

        Parent parent= parentRepository.findParentByMyUser(myUser);
        myUser.setUsername(parentDTOIn.getUsername());
        String hashPassword= new BCryptPasswordEncoder().encode(parentDTOIn.getPassword());
        myUser.setPassword(hashPassword);
        myUser.setEmail(parentDTOIn.getEmail());
        authRepository.save(myUser);
        parent.setFullName(parentDTOIn.getFullName());
        parent.setPhoneNumber(parentDTOIn.getPhoneNumber());
        parent.setAddress(parentDTOIn.getAddress());
        parentRepository.save(parent);
    }


    //Admin
    public void delete (Integer user_id){
        MyUser myUser = authRepository.findMyUserById(user_id);
        if (myUser==null){
            throw new ApiException("user not found");
        }
        Parent parent= myUser.getParent();
        parentRepository.delete(parent);
        myUser.setParent(null);
        authRepository.delete(myUser);
    }



}
