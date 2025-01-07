package com.example.tamakkun.ServiceTest;

import com.example.tamakkun.DTO_Out.SpecialistDTO_Out;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Specialist;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CentreRepository;
import com.example.tamakkun.Repository.SpecialistRepository;
import com.example.tamakkun.Service.SpecialistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class SpecialistServiceTest {


    @InjectMocks
    private SpecialistService specialistService;

    @Mock
    private SpecialistRepository specialistRepository;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private CentreRepository centreRepository;

    private MyUser myUser;
    private Centre centre;
    private Specialist specialist, specialist2;

    List<Specialist> specialists1;

    String disability;

    @BeforeEach
    void setUp() {
        myUser = new MyUser(1, "centreUser", "password", "CENTRE", "centre@example.com", null, null, null, null);
        Centre centre = new Centre(
                1,
                "Centre Name",
                "desc",
                "Riyadh",
                LocalTime.parse("11:00"),
                LocalTime.parse("22:00"),
                "123456789",
                "0123456789",
                false,
                100.0,
                "https://example.com/image.jpg",
                myUser,
                null,
                null,
                null,
                null,
                null,
                null
        );

        specialist = new Specialist(1, "Omaram", "Physiotherapist", "0557984312", 5, "imageUrl", Collections.singletonList("Down"), null, null, centre);
        specialist2 = new Specialist(2, "Nada", "Physiotherapist", "0553484312", 10, "imageUrl", null, null, null, centre);

        specialists1 = new ArrayList<>();
        specialists1.add(specialist);

        disability = "Down";  // Set the disability to match the specialist's supported disability
    }

    @Test
    public void getAllSpecialists() {

        when(specialistRepository.findAll()).thenReturn(specialists1);


        List<Specialist> specialists = specialistService.getAllSpecialists();
        Assertions.assertEquals(specialists, specialists1);

        verify(specialistRepository, times(1)).findAll();


    }

    @Test
    public void addSpecialistTest() {

        when(authRepository.findMyUserById(myUser.getId())).thenReturn(myUser);

        specialistService.addSpecialist(myUser.getId(), specialist);

        verify(authRepository, times(1)).findMyUserById(myUser.getId());
        verify(specialistRepository, times(1)).save(specialist);

    }

    @Test
    public void updateSpecialistTest() {

        when(authRepository.findMyUserById(myUser.getId())).thenReturn(myUser);

        when(specialistRepository.findSpecialistById(specialist.getId())).thenReturn(specialist);

        specialistService.updateSpecialist(specialist.getId(), specialist2, myUser.getId());

        verify(authRepository, times(1)).findMyUserById(myUser.getId());
        verify(specialistRepository, times(1)).findSpecialistById(specialist.getId());

        verify(specialistRepository, times(1)).save(specialist);

    }

    @Test
    public void deleteSpecialistTest() {

        when(authRepository.findMyUserById(myUser.getId())).thenReturn(myUser);

        when(specialistRepository.findSpecialistById(specialist.getId())).thenReturn(specialist);

        specialistService.deleteSpecialist(specialist.getId(), myUser.getId());

        verify(authRepository, times(1)).findMyUserById(myUser.getId());
        verify(specialistRepository, times(1)).findSpecialistById(specialist.getId());

        verify(specialistRepository, times(1)).delete(specialist);

    }


    @Test
    public void getSpecialistByPhoneNumberTest() {

        String phoneNumber = "0557984312";
        Integer centreId = 1;

        when(authRepository.findMyUserById(centreId)).thenReturn(myUser);

        when(specialistRepository.findByPhoneNumberAndCentreId(phoneNumber, centreId)).thenReturn(specialists1);

        List<Specialist> result = specialistService.getSpecialistByPhoneNumber(phoneNumber, centreId);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(specialist.getName());

        verify(authRepository, times(1)).findMyUserById(centreId);
        verify(specialistRepository, times(1)).findByPhoneNumberAndCentreId(phoneNumber, centreId);
    }

    @Test
    public void getSpecialistsBySupportedDisability() {
        when(authRepository.findMyUserById(myUser.getId())).thenReturn(myUser);
        when(specialistRepository.findBySupportedDisabilitiesContaining(disability)).thenReturn(specialists1); // Return specialists1

        List<Specialist> specialists = specialistService.getSpecialistsBySupportedDisability(myUser.getId(), disability);

        Assertions.assertEquals(specialists, specialists1);
        verify(authRepository, times(1)).findMyUserById(myUser.getId());
        verify(specialistRepository, times(1)).findBySupportedDisabilitiesContaining(disability);


    }


}




