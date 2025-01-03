package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Integer> {

    Specialist findSpecialistById(Integer id);
    List<Specialist> findBySupportedDisabilitiesContaining(String disabilityType);


    @Query("SELECT s, AVG(r.ratingSpecialist) as avgRating " +
            "FROM Specialist s JOIN s.reviews r " +
            "GROUP BY s " +
            "ORDER BY avgRating DESC")
    List<Specialist> findTopRatedSpecialistsWithRatings();

}
