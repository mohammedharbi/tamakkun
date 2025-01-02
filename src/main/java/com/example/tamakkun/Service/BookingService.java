package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.Model.*;
import com.example.tamakkun.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingDateRepository bookingDateRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final CentreRepository centreRepository;
    private final SpecialistRepository specialistRepository;

    public void newBooking(Integer parent_id, Integer child_id, Integer centre_id, Integer hours, Booking booking) {

        //check Parent
        Parent parent = parentRepository.findParentById(parent_id);
        if(parent==null)throw new ApiException("there is no parent found");

        // check if the child exist and check if the parent is the parent of the child
        Child child =childRepository.findChildById(child_id);
        if(child ==null)throw new ApiException("there is no child found");

        if(!child.getParent().getId().equals(parent_id))throw new ApiException("the parent isn't the parent of the child");

        //check if centre exist
        Centre centre = centreRepository.findCentreById(centre_id);
        if(centre==null)throw new ApiException("there is no centre found");

        //////////////
        //check if start time is in the opening hours and start time and the hours booked do align with range of the opening hours and closing hours
        ////////////// check local time
        LocalTime bookingStartTime = booking.getStartTime().toLocalTime();
        boolean isWithinOperatingHours;
        if (centre.getClosingHour().isBefore(centre.getOpeningHour())) { // Handles crossing midnight
            isWithinOperatingHours =
                    (bookingStartTime.isAfter(centre.getOpeningHour()) || bookingStartTime.equals(centre.getOpeningHour())) ||
                            (bookingStartTime.isBefore(centre.getClosingHour()) || bookingStartTime.equals(centre.getClosingHour()));
        } else { // not crossing midnight
            isWithinOperatingHours =
                    (bookingStartTime.isAfter(centre.getOpeningHour()) || bookingStartTime.equals(centre.getOpeningHour())) &&
                            (bookingStartTime.isBefore(centre.getClosingHour()) || bookingStartTime.equals(centre.getClosingHour()));
        }

        if (!isWithinOperatingHours) {throw new ApiException("Booking is outside operating hours.");}


        //check if there is an specialist with supportedDisabilities same as the child disabilities

        for (Specialist s: centre.getSpecialists()) {

            if (checkIfSupportedDisabilities(s, child)){//
                //check if this specialist isn't booked for this booking start and end date
                for (BookingDate bookingDate:bookingDateRepository.findAll()){
                    if (bookingDate.getSpecialist().getId().equals(s.getId())){
                        if (!bookingDate.getStartTime().equals(booking.getStartTime()) && bookingDate.getEndTime().equals(booking.getStartTime().plusHours(hours))){
                            BookingDate newBookingDate = new BookingDate(null,booking.getStartTime(),booking.getStartTime().plusHours(hours),booking,centre,s);
                            Booking newBooking = new Booking(null,booking.getStartTime(),hours,"Completed",centre.getPricePerHour()*hours,newBookingDate,parent,child,centre);
                            bookingRepository.save(newBooking);
                            bookingDateRepository.save(newBookingDate);
                        }
                    }
                }

            }else throw new ApiException("there is no specialist found with supportedDisabilities same as the child disabilities");
        }
    }

    public Boolean checkIfSupportedDisabilities(Specialist specialist, Child child){ // it will check if specialists has a supported disabilities matching the child disabilities and return a boolean

        for (String disability: specialist.getSupportedDisabilities()){
            if (disability.equals(child.getDisabilityType())){return true;}
        }
        return false;
    }
}
