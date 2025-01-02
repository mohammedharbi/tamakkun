package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.BookingDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDateRepository extends JpaRepository<BookingDate, Integer> {

    BookingDate findBookingDateById(Integer bookingId);
}
