package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {

    Parent findParentById(Integer id);
    Parent findParentByMyUser (MyUser myUser);

}
