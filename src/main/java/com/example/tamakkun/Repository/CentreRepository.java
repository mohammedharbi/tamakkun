package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Centre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentreRepository extends JpaRepository<Centre, Integer> {

    Centre findCentreById(Integer id);
}
