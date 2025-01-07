package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findReviewById(Integer reviewId);

    @Query("select o from Booking o where o.parent.myUser.id=?1 and o.centre.id=?2 and o.status='Completed'")
    List<Booking> findCompletedBookingsByMyUserAndCentre(Integer userId, Integer centreId);

    List<Review> findReviewByCentre(Centre centre);

    @Query("select r.specialist FROM Review r " +
            "WHERE r.centre.id = ?1 " +
            "GROUP BY r.specialist.id " +
            "ORDER BY AVG(r.ratingSpecialist) DESC")
    List<Specialist> findTopThreeRatedSpecialistsByCentre(Integer centreId);

    List<Review> findReviewByParent(Parent parent);



}
