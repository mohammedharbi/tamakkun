package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Booking;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Booking findBookingById(Integer bookingId);
    List<Booking> findBookingsByCentre(Centre centre);
    List<Booking> findBookingsByParent(Parent parent);
}
