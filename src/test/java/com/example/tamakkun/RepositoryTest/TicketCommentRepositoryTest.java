package com.example.tamakkun.RepositoryTest;

import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Ticket;
import com.example.tamakkun.Model.TicketComment;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.TicketCommentRepository;
import com.example.tamakkun.Repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TicketCommentRepositoryTest {

    @Autowired
    private TicketCommentRepository ticketCommentRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private TicketRepository ticketRepository;

    Ticket ticket1;

    TicketComment comment1, comment2;

    MyUser user1;

    @BeforeEach
    public void setUp() {

        user1 = new MyUser(null, "user1", "password1", "USER", "user1@example.com", null, null, null, null);
        authRepository.save(user1);

        // Create and save Ticket
        ticket1 = new Ticket(null, "COMPLAINT", "Description 1", "open", LocalDate.now(), null, user1, null, null, null, null, null);
        ticketRepository.save(ticket1);

        // Set up sample TicketComments
        comment1 = new TicketComment(null, "This is comment 1", null,ticket1,user1);
        comment2 = new TicketComment(null, "This is comment 2", null, ticket1, user1);

        // Save TicketComments to the repository
        ticketCommentRepository.save(comment1);
        ticketCommentRepository.save(comment2);
    }

    @Test
    public void testFindByTicketId() {
        // Retrieve Ticket from the repository
          // Ensure ticket with ID 1 exists
        List<TicketComment> comments = ticketCommentRepository.findTicketCommentByTicketId(ticket1.getId());

        // Check that comments are not empty
        assertThat(comments).isNotEmpty();
        assertThat(comments.size()).isEqualTo(2);  // Assuming 2 comments were added
    }

    @Test
    public void testFindByTicketIdNotFound() {
        // Test for a non-existing ticket_id
        List<TicketComment> comments = ticketCommentRepository.findTicketCommentByTicketId(999);

        // Assert that no comments are found for the non-existing ticket_id
        assertThat(comments).isEmpty();
    }
}
