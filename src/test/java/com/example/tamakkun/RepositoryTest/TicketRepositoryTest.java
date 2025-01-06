package com.example.tamakkun.RepositoryTest;

import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Ticket;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.TicketRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AuthRepository authRepository;

    private MyUser user1, user2;
    private Ticket ticket1, ticket2, ticket3;

    @BeforeEach
    void setUp() {
        // Create Users
        user1 = new MyUser(null, "user1", "password1", "USER", "user1@example.com", null, null, null, null);
        user2 = new MyUser(null, "user2", "password2", "USER", "user2@example.com", null, null, null, null);
        authRepository.save(user1);
        authRepository.save(user2);

        // Create Tickets
        ticket1 = new Ticket(null, "COMPLAINT", "Description 1", "open", LocalDate.now(), null, user1, null, null, null, null, null);
        ticket2 = new Ticket(null, "SUGGESTION", "Description 2", "in_progress", LocalDate.now(), null, user1, null, null, null, null, null);
        ticket3 = new Ticket(null, "COMPLAINT", "Description 3", "close", LocalDate.now(), null, user2, null, null, null, null, null);

        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        ticketRepository.save(ticket3);
    }

    @Test
    public void testFindTicketById() {
        Ticket foundTicket = ticketRepository.findTicketById(ticket1.getId());
        Assertions.assertThat(foundTicket).isNotNull();
        Assertions.assertThat(foundTicket.getDescription()).isEqualTo("Description 1");
    }

    @Test
    public void testFindByStatus() {
        List<Ticket> openTickets = ticketRepository.findByStatus("open");
        Assertions.assertThat(openTickets).isNotEmpty();
        Assertions.assertThat(openTickets.size()).isEqualTo(1);
        Assertions.assertThat(openTickets.get(0).getDescription()).isEqualTo("Description 1");
    }

    @Test
    public void testFindByCreatedBy() {
        List<Ticket> user1Tickets = ticketRepository.findByCreatedBy(user1);
        Assertions.assertThat(user1Tickets).isNotEmpty();
        Assertions.assertThat(user1Tickets.size()).isEqualTo(2);
        Assertions.assertThat(user1Tickets).extracting("description").contains("Description 1", "Description 2");
    }

    @Test
    public void testFindTicketByIdNotFound() {
        Ticket foundTicket = ticketRepository.findTicketById(999);
        Assertions.assertThat(foundTicket).isNull();
    }

    @Test
    public void testFindByStatusNotFound() {
        List<Ticket> noTickets = ticketRepository.findByStatus("nonexistent_status");
        Assertions.assertThat(noTickets).isEmpty();
    }
}