package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Child;
import com.example.tamakkun.Model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {

    Child findChildById(Integer id);
    List<Child> findAllByParent (Parent parent);

}
