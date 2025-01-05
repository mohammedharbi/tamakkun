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

    @Query(value = "SELECT r.specialist_id, AVG(r.rating_specialist) as avgRating " +
            "FROM review r " +
            "WHERE r.centre_id = :centre_id " +
            "GROUP BY r.specialist_id " +
            "ORDER BY avgRating DESC " +
            "LIMIT 3", nativeQuery = true)
    List<Object[]> findTopThreeRatedSpecialists(Integer centreId);

    List<Review> findReviewByParent(Parent parent);



}
