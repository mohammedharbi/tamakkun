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
     SpecialistRepository specialistRepository;

    @Autowired
     CentreRepository centreRepository;

    @Autowired
     AuthRepository authRepository;

     Centre centre1;
     Specialist specialist1, specialist2, specialist3;

    @BeforeEach
    public void setUp() {
        // Create a MyUser object first
        MyUser user1 = new MyUser(null, "JoyOasis", "password334", "CENTRE", "JoyOasis@Tamakkun.com", null, null, null, null);
        authRepository.save(user1);

        // Create a Centre and link it with the MyUser object
        LocalTime openingHour = LocalTime.of(18,00);
        LocalTime closingHour = LocalTime.of(9,00);

        centre1 = new Centre(null, "Centre A", "Description A", "123 Main St", openingHour, closingHour, "1234567890", "0501234567", true, 100.0, "https://image.com/img1", user1, null, null,  null, null, null, null);
        centreRepository.save(centre1);

        // Create Specialists
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
    public void testFindByNameAndCentreId() {
        Specialist foundSpecialist = specialistRepository.findByNameAndCentreId("Jane Smith", centre1.getId());
        Assertions.assertThat(foundSpecialist).isNotNull();
        Assertions.assertThat(foundSpecialist.getSpecialization()).isEqualTo("Physical Therapy");
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
