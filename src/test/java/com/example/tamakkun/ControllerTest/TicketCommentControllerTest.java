package com.example.tamakkun.ControllerTest;

import com.example.tamakkun.Controller.TicketCommentController;
import com.example.tamakkun.DTO_In.TicketCommentDTO_In;
import com.example.tamakkun.DTO_Out.TicketCommentDTO_Out;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Service.TicketCommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TicketCommentController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class TicketCommentControllerTest {



    @MockBean
    private TicketCommentService ticketCommentService;

    @Autowired
    private MockMvc mockMvc;

    private MyUser myUser;
    private TicketCommentDTO_In ticketCommentDTOIn;
    private TicketCommentDTO_Out ticketCommentDTOOut;
    private List<TicketCommentDTO_Out> comments;

    @BeforeEach
    void setUp() {
        myUser = new MyUser(1, "Ahmed", "12345", "USER", "ahmed@example.com", null, null, null, null);

        ticketCommentDTOIn = new TicketCommentDTO_In("This is a test comment!");
        ticketCommentDTOOut = new TicketCommentDTO_Out(
                "This is a test comment!",
                LocalDate.now(),
                "Ahmed"
        );

        comments = Arrays.asList(ticketCommentDTOOut);
    }

    @Test
    public void testAddComment() throws Exception {
        doNothing().when(ticketCommentService).addComment(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(TicketCommentDTO_In.class));

        mockMvc.perform(post("/api/v1/tamakkun-system/ticket-comment/add-comment/{ticket_id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new TicketCommentDTO_In("Test comment"))))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetCommentsByTicket() throws Exception {
        mockMvc.perform(get("/api/v1/tamakkun-system/ticket-comment/get-comments-by-ticket/{ticket_id}", 1))
                .andExpect(status().isOk());
    }



}
