package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Booking findBookingById(Integer bookingId);
}
