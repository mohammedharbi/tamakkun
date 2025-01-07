package com.example.tamakkun.ServiceTest;
import com.example.tamakkun.DTO_In.TicketCommentDTO_In;
import com.example.tamakkun.DTO_Out.TicketCommentDTO_Out;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Ticket;
import com.example.tamakkun.Model.TicketComment;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.TicketCommentRepository;
import com.example.tamakkun.Repository.TicketRepository;
import com.example.tamakkun.Service.TicketCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketPostCommentServiceTest {

    @InjectMocks
    TicketCommentService ticketCommentService;

    @Mock
    TicketRepository ticketRepository;

    @Mock
    AuthRepository authRepository;

    @Mock
    TicketCommentRepository ticketCommentRepository;

    MyUser myUser;
    Ticket ticket1, ticket2;
    TicketComment ticketComment1;
    TicketCommentDTO_In ticketCommentDTOIn;
    TicketCommentDTO_Out ticketCommentDTOOut;

    List<TicketCommentDTO_Out> ticketCommentDTOOuts;

    @BeforeEach
    void setUp() {
        myUser = new MyUser(1, "Durra23", "124", "PARENT", "durra@gmail.com", null, null, null, null);
        ticket1 = new Ticket(1, "type", "desc", "open", LocalDate.now(), LocalDate.now(), null, null, null, null, null, null);
        ticket1.setCreatedBy(myUser);
        ticketComment1 = new TicketComment(1, "This is a comment", LocalDate.now(), ticket1, myUser);
        ticketCommentDTOIn = new TicketCommentDTO_In("This is a comment");
        ticketCommentDTOOut = new TicketCommentDTO_Out("This is a comment", LocalDate.now(), myUser.getUsername());

        ticketCommentDTOOuts = new ArrayList<>();
        ticketCommentDTOOuts.add(ticketCommentDTOOut);
    }

    @Test
    public void addCommentTest() {
        when(authRepository.findMyUserById(myUser.getId())).thenReturn(myUser);
        when(ticketRepository.findTicketById(ticket1.getId())).thenReturn(ticket1);
        when(ticketCommentRepository.save(any(TicketComment.class))).thenReturn(ticketComment1);

        ticketCommentService.addComment(myUser.getId(), ticket1.getId(), ticketCommentDTOIn);


        verify(authRepository, times(1)).findMyUserById(myUser.getId());
        verify(ticketRepository, times(1)).findTicketById(ticket1.getId());
        verify(ticketCommentRepository, times(1)).save(any(TicketComment.class));
    }

    @Test
    void getCommentsByTicket() {
        //
        Integer userId = 1;
        Integer ticketId = 1;
        MyUser user = new MyUser(userId, "durra23", "password", "PARENT", "durra@gmail.com", null, null, null, null);
        Ticket ticket = new Ticket(ticketId, "type", "desc", "open", LocalDate.now(), LocalDate.now(), null, null, null, null, null, null);
        ticket.setCreatedBy(user);
        TicketComment ticketComment = new TicketComment(1, "This is a comment", LocalDate.now(), ticket, user);

        when(authRepository.findMyUserById(userId)).thenReturn(user);
        when(ticketRepository.findTicketById(ticketId)).thenReturn(ticket);
        when(ticketCommentRepository.findTicketCommentByTicketId(ticketId)).thenReturn(Arrays.asList(ticketComment));


        List<TicketCommentDTO_Out> result = ticketCommentService.getCommentsByTicket(userId, ticketId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("This is a comment", result.get(0).getContent());

        verify(authRepository, times(1)).findMyUserById(userId);
        verify(ticketRepository, times(1)).findTicketById(ticketId);
        verify(ticketCommentRepository, times(1)).findTicketCommentByTicketId(ticketId);
    }


}
