package com.example.tamakkun.RepositoryTest;


import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Specialist;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CentreRepository;
import com.example.tamakkun.Repository.SpecialistRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpecialistRepositoryTest {

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private CentreRepository centreRepository;

    @Autowired
    private AuthRepository authRepository;

    private Centre centre1;
    private Specialist specialist1;
    private Specialist specialist2;
    private Specialist specialist3;


    @BeforeEach
    public void setUp() {

        LocalTime opening = LocalTime.of(10,00);

        LocalTime closing = LocalTime.of(22,00);
        MyUser user = new MyUser(null, "CentreUser", "password123", "CENTRE", "centreuser@example.com", null, null, null, null);
        authRepository.save(user);

        centre1 = new Centre(null, "Centre A", "Description A", "123 Main St", opening, closing, "1234567890", "0501234567", true, 100.0, "https://image.com/img1", user, null, null, null, null, null, null);
        centreRepository.save(centre1);

        specialist1 = new Specialist(null, "John Doe", "Speech Therapy", "0501234567", 5, "https://image.com/img2", Arrays.asList("AUTISM", "DOWN_SYNDROME"), null, null, centre1);
        specialist2 = new Specialist(null, "Jane Smith", "Physical Therapy", "0509876543", 7, "https://image.com/img3", Arrays.asList("PHYSICAL_DISABILITY", "SENSORY_DISABILITY"), null, null, centre1);
        specialist3 = new Specialist(null, "Emma Brown", "Occupational Therapy", "0507654321", 4, "https://image.com/img4", Arrays.asList("SPEECH_AND_LANGUAGE_DISORDERS", "AUTISM"), null, null, centre1);

        specialistRepository.save(specialist1);
        specialistRepository.save(specialist2);
        specialistRepository.save(specialist3);
    }

    @Test
    public void testFindSpecialistById() {
        Specialist foundSpecialist = specialistRepository.findSpecialistById(specialist1.getId());
        Assertions.assertThat(foundSpecialist).isNotNull();
        Assertions.assertThat(foundSpecialist.getName()).isEqualTo("John Doe");
    }

    @Test
    public void testFindBySupportedDisabilitiesContaining() {
        List<Specialist> specialists = specialistRepository.findBySupportedDisabilitiesContaining("AUTISM");
        Assertions.assertThat(specialists).isNotEmpty();
        Assertions.assertThat(specialists.size()).isEqualTo(2);
        Assertions.assertThat(specialists).extracting("name").contains("John Doe", "Emma Brown");
    }

    @Test
    public void testFindByPhoneNumberAndCentreId() {
        List<Specialist> specialists = specialistRepository.findByPhoneNumberAndCentreId("0501234567", centre1.getId());
        Assertions.assertThat(specialists).isNotEmpty();
        Assertions.assertThat(specialists.get(0).getName()).isEqualTo("John Doe");
    }

    @Test
    public void testFindByPhoneNumberAndCentreId_NotFound() {
        List<Specialist> specialists = specialistRepository.findByPhoneNumberAndCentreId("0500000000", centre1.getId());
        Assertions.assertThat(specialists).isEmpty();
    }

    @Test
    public void testFindSpecialistByIdNotFound() {
        Specialist foundSpecialist = specialistRepository.findSpecialistById(999);
        Assertions.assertThat(foundSpecialist).isNull();
    }

    @Test
    public void testFindBySupportedDisabilitiesContainingNotFound() {
        List<Specialist> specialists = specialistRepository.findBySupportedDisabilitiesContaining("NON_EXISTENT_DISABILITY");
        Assertions.assertThat(specialists).isEmpty();
    }
}