package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_In.CentreDTO_In;
import com.example.tamakkun.DTO_Out.ActivityDTO_Out;
import com.example.tamakkun.DTO_Out.BookingDTO_Out;
import com.example.tamakkun.DTO_Out.CentreDTO_Out;
import com.example.tamakkun.DTO_Out.SpecialistDTO_Out;
import com.example.tamakkun.Model.*;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.BookingRepository;
import com.example.tamakkun.Repository.CentreRepository;
import com.example.tamakkun.Repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CentreService {

    private final CentreRepository centreRepository;
    private final AuthRepository authRepository;
    private final SpecialistRepository specialistRepository;
    private final TextToSpeechService textToSpeechService;
    private final BookingRepository bookingRepository;


    //For admin --> more details about centre to him
    public List<Centre> getAllCentres(){
        return centreRepository.findAll();
    }

    //For all
    public List<CentreDTO_Out> getCentres() {
        return centreRepository.findAll().stream()
                .map(centre -> new CentreDTO_Out(
                        centre.getName(),
                        centre.getDescription(),
                        centre.getAddress(),
                        centre.getOpeningHour(),
                        centre.getClosingHour(),
                        centre.getPricePerHour(),
                        centre.getImageUrl()
                ))
                .collect(Collectors.toList());
    }


    public void centreRegister(CentreDTO_In centreDTOIn) {

        if (centreDTOIn.getOpeningHour().isAfter(centreDTOIn.getClosingHour()) ||
                centreDTOIn.getOpeningHour().equals(centreDTOIn.getClosingHour())) {
            throw new ApiException("Opening hour must be before closing hour!");
        }


        MyUser myUser = new MyUser();
        try {
        myUser.setUsername(centreDTOIn.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(centreDTOIn.getPassword());
        myUser.setPassword(hashPassword);
        myUser.setEmail(centreDTOIn.getEmail());

        myUser.setRole("CENTRE");

        Centre centre = new Centre();

        centre.setName(centreDTOIn.getName());
        centre.setDescription(centreDTOIn.getDescription());
        centre.setAddress(centreDTOIn.getAddress());
        centre.setOpeningHour(centreDTOIn.getOpeningHour());
        centre.setClosingHour(centreDTOIn.getClosingHour());
        centre.setCommercialLicense(centreDTOIn.getCommercialLicense());
        centre.setPhoneNumber(centreDTOIn.getPhoneNumber());
        centre.setPricePerHour(centreDTOIn.getPricePerHour());
        centre.setImageUrl(centreDTOIn.getImageUrl());

        centre.setMyUser(myUser);

        //save user and centre

        authRepository.save(myUser);
        centreRepository.save(centre);
        }catch (Exception e){
            if(myUser!=null){
                authRepository.delete(myUser);
            }
            throw new ApiException("Failed to register parent:"+e.getMessage());
        }
    }


    public void updateCentre(CentreDTO_In centreDTOIn, Integer user_id){

        Centre oldCentre = centreRepository.findCentreById(user_id);


        MyUser oldUser = authRepository.findMyUserById(oldCentre.getMyUser().getId());
        if(oldUser==null)
            throw new ApiException("User not found!");

        if(oldUser.getId() != user_id)
            throw new ApiException("You cannot update this centre!");

        oldUser.setUsername(centreDTOIn.getUsername());
        oldUser.setEmail(centreDTOIn.getEmail());
        oldUser.setPassword(new BCryptPasswordEncoder().encode(centreDTOIn.getPassword()));

        oldCentre.setName(centreDTOIn.getName());
        oldCentre.setAddress(centreDTOIn.getAddress());
        oldCentre.setDescription(centreDTOIn.getDescription());
        oldCentre.setPhoneNumber(centreDTOIn.getPhoneNumber());
        oldCentre.setOpeningHour(centreDTOIn.getOpeningHour());
        oldCentre.setClosingHour(centreDTOIn.getClosingHour());
        oldCentre.setPricePerHour(centreDTOIn.getPricePerHour());
        oldCentre.setImageUrl(centreDTOIn.getImageUrl());

        authRepository.save(oldUser);
        centreRepository.save(oldCentre);


    }

    //by centre itself? or admin?
    public void deleteCentre(Integer user_id){

        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("User not found!");
        }

        Centre centre= centreRepository.findCentreById(user_id);
        if(centre==null)
            throw new ApiException("Center not found!");

        authRepository.delete(user);

    }


    //3: Durrah

    public List<CentreDTO_Out> filterCentresByPrice(Integer user_id, Double minPrice, Double maxPrice) {

        MyUser user = authRepository.findMyUserById(user_id);
        if(user==null)
            throw new ApiException("User not found!");

        return centreRepository.findAll().stream()
                .filter(centre -> centre.getPricePerHour() >= minPrice && centre.getPricePerHour() <= maxPrice)
                .map(centre -> new CentreDTO_Out(
                        centre.getName(),
                        centre.getDescription(),
                        centre.getAddress(),
                        centre.getOpeningHour(),
                        centre.getClosingHour(),
                        centre.getPricePerHour(),
                        centre.getImageUrl()
                ))
                .collect(Collectors.toList());
    }

    public CentreDTO_Out getCentreByName(Integer user_id, String name) {

        MyUser user = authRepository.findMyUserById(user_id);
        if(user==null)
            throw new ApiException("User not found!");

        // use the repository method to fetch centre
        Centre centre = centreRepository.findCentreByNameContaining(name);

        if (centre == null) {
            throw new ApiException("Centre with name " + name + " not found!");
        }

        return new CentreDTO_Out(
                centre.getName(),
                centre.getDescription(),
                centre.getAddress(),
                centre.getOpeningHour(),
                centre.getClosingHour(),
                centre.getPricePerHour(),
                centre.getImageUrl()
        );
    }

    public List<CentreDTO_Out> getCentresByAddress(Integer user_id, String address) {

        MyUser user = authRepository.findMyUserById(user_id);
        if(user==null)
            throw new ApiException("User not found!");
        // retrive centres by address
        List<Centre> centres = centreRepository.findByAddressContainingIgnoreCase(address);

        // convert it to DTO
        return centres.stream()
                .map(centre -> new CentreDTO_Out(
                        centre.getName(),
                        centre.getDescription(),
                        centre.getAddress(),
                        centre.getOpeningHour(),
                        centre.getClosingHour(),
                        centre.getPricePerHour(),
                        centre.getImageUrl()))
                .collect(Collectors.toList());
    }

    public List<CentreDTO_Out> getCentresByHoursRange(Integer user_id, LocalTime startOpening, LocalTime endClosing) {

        MyUser user = authRepository.findMyUserById(user_id);
        if(user==null)
            throw new ApiException("User not found!");
        // specified time range
        List<Centre> centres = centreRepository.findCentresByOpeningAndClosingHourRange(startOpening, endClosing);

        return centres.stream()
                .map(centre -> new CentreDTO_Out(
                        centre.getName(),
                        centre.getDescription(),
                        centre.getAddress(),
                        centre.getOpeningHour(),
                        centre.getClosingHour(),
                        centre.getPricePerHour(),
                        centre.getImageUrl()))
                .collect(Collectors.toList());
    }


    public List<Specialist> getAllSpecialistsByCentre( Integer centreId) {

        Centre centre = centreRepository.findById(centreId)
                .orElseThrow(() -> new ApiException("Centre not found!"));

        return specialistRepository.findAll();

    }


    public Centre getMyCentre(Integer centreId) {
        return centreRepository.findCentreById(centreId);
    }



    public byte[] getCentreDescriptionAsAudio(Integer centreId) {
        // الحصول على المركز
        Centre centre = centreRepository.findById(centreId)
                .orElseThrow(() -> new RuntimeException("Centre not found"));

        // تحويل الوصف إلى صوت
        return textToSpeechService.convertTextToAudio(centre.getDescription());
    }

    public List<CentreDTO_Out> getTop5CenterByAvrRating (){
        return convertCentreToDTO(centreRepository.findTop5ByAverageRating());
    }


    public List<CentreDTO_Out> convertCentreToDTO(Collection<Centre> centres){
        List<CentreDTO_Out> centreDTO_outs = new ArrayList<>();
        for(Centre c : centres){
            centreDTO_outs.add(new CentreDTO_Out(c.getName(),c.getDescription(),c.getAddress(),c.getOpeningHour(),c.getClosingHour(),c.getPricePerHour(),c.getImageUrl()));
        }
        return centreDTO_outs;
    }

    //E:#4 Mohammed
    public List<BookingDTO_Out> getAllOldBookingsByCentre(Integer centre_id){// need to be tested

        List<BookingDTO_Out> oldBookings = new ArrayList<>();

        // check if centre exist
        Centre centre = centreRepository.findCentreById(centre_id);
        if (centre == null) {throw new ApiException("Centre not found!");}

        // get all bookings with the same center
        List<Booking> bookings = bookingRepository.findBookingsByCentre(centre);
        if (bookings == null) {throw new ApiException("Not found any bookings for the centre!");}

        // get only old bookings
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Booking booking : bookings) {
            if (booking.getBookingDate().getStartTime().isBefore(currentDateTime)) {
                //change it to DTO OUT
                BookingDTO_Out bookingDTO = new BookingDTO_Out(booking.getParent().getFullName(),
                        booking.getChild().getFullName(),booking.getBookingDate().getSpecialist().getName(),
                        booking.getStartTime().toLocalDate(),booking.getStartTime().toLocalTime(),booking.getHours(),
                        booking.getTotalPrice());
                oldBookings.add(bookingDTO);
            }
        }

        if (oldBookings.isEmpty()){throw new ApiException("Not found any old bookings for the centre!");}

        return oldBookings;
    }

    //E:#5 Mohammed
    public List<BookingDTO_Out> getAllNewBookingsByCentre(Integer centre_id){

        List<BookingDTO_Out> newBookings = new ArrayList<>();

        // check if centre exist
        Centre centre = centreRepository.findCentreById(centre_id);
        if (centre == null) {throw new ApiException("Centre not found!");}

        // get all bookings with the same center
        List<Booking> bookings = bookingRepository.findBookingsByCentre(centre);
        if (bookings == null) {throw new ApiException("Not found any bookings for the centre!");}
        // get only new bookings
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Booking booking : bookings) {
            if (booking.getBookingDate().getStartTime().isAfter(currentDateTime)) {
                //change it to DTO OUT
                BookingDTO_Out bookingDTO = new BookingDTO_Out(booking.getParent().getFullName(),
                        booking.getChild().getFullName(),booking.getBookingDate().getSpecialist().getName(),
                        booking.getStartTime().toLocalDate(),booking.getStartTime().toLocalTime(),booking.getHours(),
                        booking.getTotalPrice());
                newBookings.add(bookingDTO);
            }
        }

        if (newBookings.isEmpty()){throw new ApiException("Not found any new bookings for the centre!");}

        return newBookings;
    }










    }
