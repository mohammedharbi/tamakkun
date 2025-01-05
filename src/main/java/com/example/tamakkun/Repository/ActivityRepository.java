package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    Activity findActivityById(Integer id);

    @Query("SELECT a FROM Activity a JOIN a.allowedDisabilities ad WHERE :disabilityType = ad")
    List<Activity> findActivitiesByDisabilityType( String disabilityType);

}
