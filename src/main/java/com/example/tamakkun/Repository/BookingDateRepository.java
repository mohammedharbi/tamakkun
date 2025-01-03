package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.BookingDate;
import com.example.tamakkun.Model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDateRepository extends JpaRepository<BookingDate, Integer> {

    BookingDate findBookingDateById(Integer bookingId);
    List<BookingDate> findAllBySpecialist(Specialist specialist);
}
