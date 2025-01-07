package com.example.tamakkun.ControllerTest;

import com.example.tamakkun.Controller.ReviewController;
import com.example.tamakkun.Controller.TicketCommentController;
import com.example.tamakkun.Service.ReviewService;
import com.example.tamakkun.Service.TicketCommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ReviewController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ReviewControllerTest {

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTopThreeRatedSpecialists() throws Exception {
        when(reviewService.getTopThreeRatedSpecialistsByCentre(Mockito.anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/tamakkun-system/review/top-three-specialists"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetReviewsByCentre() throws Exception {
        when(reviewService.getReviewsByCentre(Mockito.anyInt())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/tamakkun-system/review/reviews/byCentre"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllReviews() throws Exception {
        when(reviewService.getAllReviews()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/tamakkun-system/review/get-All-reviews"))
                .andExpect(status().isOk());

    }


}