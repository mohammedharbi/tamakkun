package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Integer> {

    Specialist findSpecialistById(Integer id);

    List<Specialist> findBySupportedDisabilitiesContaining(String disabilityType);


    @Query("SELECT s FROM Specialist s WHERE s.name = :name AND s.centre.id = :centreId")
    Specialist findByNameAndCentreId(String name, Integer centreId);



}
