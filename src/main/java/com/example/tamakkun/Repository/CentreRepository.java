package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Activity;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface CentreRepository extends JpaRepository<Centre, Integer> {

    Centre findCentreById(Integer id);

    Centre findCentreByName(String name);

    // to find centres by address case-insensitive search
    List<Centre> findByAddressContainingIgnoreCase(String address);

    @Query("SELECT c FROM Centre c WHERE c.openingHour BETWEEN :startOpening AND :endClosing AND c.closingHour BETWEEN :startOpening AND :endClosing")
    List<Centre> findCentresByOpeningAndClosingHourRange(LocalTime startOpening, LocalTime endClosing);

    @Query("SELECT a FROM Activity a WHERE a.centre.id = :centreId")
    List<Activity> findActivitiesByCentre(Integer centreId);

    @Query("SELECT  c FROM Centre c WHERE c.isVerified = false")
    List<Centre> findUnverifiedCentres();

    @Query("select c from Centre c join c.reviews r group by c.id order by AVG (r.ratingCentre) desc ")
    List<Centre> findTop5ByAverageRating ();

    Centre findCentreByMyUser (MyUser user);
}
