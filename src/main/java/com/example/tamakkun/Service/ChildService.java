package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_Out.ChildDTO_Out;
import com.example.tamakkun.Model.Child;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Parent;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.ChildRepository;
import com.example.tamakkun.Repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildService {
    private final ChildRepository childRepository;
    private final AuthRepository authRepository;
    private final ParentRepository parentRepository;



    public List<ChildDTO_Out> getMyChildren(Integer user_id){
        MyUser user = authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("wrong user id");
        }
        Parent parent= user.getParent();
        if(parent==null){
            throw new ApiException("no parent associate with this user");}
        return convertChildToDTO(childRepository.findAllByParent(parent));
    }

    //#

    public ChildDTO_Out getChildById (Integer user_id ,Integer child_id){
        MyUser user = authRepository.findMyUserById(user_id);
        Parent parent= parentRepository.findParentByMyUser(user);
        if(parent==null){
            throw new ApiException("no parent associate with this user");}
        Child child= childRepository.findChildById(child_id);
        if (child==null){
            throw new ApiException("child not found");}
        if(!child.getParent().getId().equals(parent.getId())){
            throw new ApiException("this child is not belong to logged in user ");}
        return new ChildDTO_Out(child.getFullName(), child.getDisabilityType(), child.getAge());

    }


    public void addChild (Integer user_id , Child child){
        MyUser myUser= authRepository.findMyUserById(user_id);
        if(myUser==null){
            throw new ApiException("user not found");
        }
        Parent parent= parentRepository.findParentByMyUser(myUser);
        if (parent==null){
            throw new ApiException("no parent associate with this user");
        }
        child.setParent(parent);
        childRepository.save(child);
    }

    public void update (Integer user_id, Integer child_id, Child child){
        MyUser myUser = authRepository.findMyUserById(user_id);
        if (myUser==null){
            throw new ApiException("user not found");}
        Parent parent= parentRepository.findParentByMyUser(myUser);
        if(parent==null){
            throw new ApiException("no parent associate with this user");}
        Child old= childRepository.findChildById(child_id);
        if (old==null){
            throw new ApiException("child not found");}
        if(!old.getParent().getId().equals(parent.getId())){
            throw new ApiException("this child is not belong to logged in user ");}
        old.setFullName(child.getFullName());
        old.setDisabilityType(child.getDisabilityType());
        old.setAge(child.getAge());
        childRepository.save(old);
    }

    public void delete (Integer user_id, Integer child_id){
        MyUser myUser = authRepository.findMyUserById(user_id);
        Parent parent= parentRepository.findParentByMyUser(myUser);
        if(parent==null){
            throw new ApiException("no parent associate with this user");}
        Child child= childRepository.findChildById(child_id);
        if (child==null){
            throw new ApiException("child not found");}
        if(!child.getParent().getId().equals(parent.getId())){
            throw new ApiException("this child is not belong to logged in user ");}
        childRepository.delete(child);
    }

    public List<ChildDTO_Out> convertChildToDTO(Collection<Child> children){
        List<ChildDTO_Out> childDTOOuts = new ArrayList<>();
        for(Child c : children){
            childDTOOuts.add(new ChildDTO_Out(c.getFullName(),c.getDisabilityType(),c.getAge()));
        }
        return childDTOOuts;
    }



}
