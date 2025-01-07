package com.example.tamakkun.ControllerTest;

import com.example.tamakkun.Controller.AuthController;
import com.example.tamakkun.Controller.ChildController;
import com.example.tamakkun.DTO_Out.ChildDTO_Out;
import com.example.tamakkun.DTO_Out.CommunityDTO_out;
import com.example.tamakkun.Model.Child;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Parent;
import com.example.tamakkun.Service.AuthService;
import com.example.tamakkun.Service.ChildService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ChildController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ChildControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ChildService childService;



    private MyUser myUser;
    private ChildDTO_Out child1, child2;
    private List<ChildDTO_Out> children;

    @BeforeEach
    void setUp() {

    }
    @Test
    @WithMockUser
    public void testGetChildWithCustomUserWithoutActualVerification() throws Exception {
        MyUser customUser = new MyUser(1, "testUser", "12345", "USER", "test@example.com", null, null, null, null);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(customUser, null, List.of(new SimpleGrantedAuthority("ROLE_USER")))
        );

        Integer childId = 1;
        ChildDTO_Out childDTO = new ChildDTO_Out("John Doe", "Visual Impairment", 10);
        when(childService.getChildById(Mockito.eq(customUser.getId()), Mockito.eq(childId))).thenReturn(childDTO);

        mockMvc.perform(get("/api/v1/tamakkun-system/child/get-child/{child_id}", childId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddChild() throws Exception {
        ChildDTO_Out childDTO = new ChildDTO_Out("Jane Doe", "PHYSICAL_DISABILITY", 8);

        mockMvc.perform(post("/api/v1/tamakkun-system/child/add-child")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(childDTO)))
                .andExpect(status().isOk());}
    @Test
    public void testUpdateChild() throws Exception {
        Integer childId = 1;
        ChildDTO_Out updatedChildDTO = new ChildDTO_Out("John Updated", "PHYSICAL_DISABILITY", 11);
        mockMvc.perform(put("/api/v1/tamakkun-system/child/update/{child_id}", childId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedChildDTO)))
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteChild() throws Exception {
        Integer childId = 1;

        mockMvc.perform(delete("/api/v1/tamakkun-system/child/delete/{child_id}", childId))
                .andExpect(status().isOk());
    }
}
