package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Booking;
import com.example.tamakkun.Model.BookingDate;
import com.example.tamakkun.Model.Parent;
import com.example.tamakkun.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findReviewById(Integer reviewId);

    @Query("select o from Booking o where o.parent.myUser.id=?1 and o.centre.id=?2 and o.isReviewed= false ")
    List<Booking> findCompletedOffersByMyUserAndCentre(Integer userId, Integer centreId);

    List<Review> findReviewByParent(Parent parent);
}
