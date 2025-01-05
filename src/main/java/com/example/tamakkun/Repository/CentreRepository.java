package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CentreRepository extends JpaRepository<Centre, Integer> {

    Centre findCentreById(Integer id);

    @Query("select c from Centre c join c.reviews r group by c.id order by AVG (r.ratingCentre) desc ")
    List<Centre> findTop5ByAverageRating ();

    Centre findCentreByMyUser (MyUser user);
}
