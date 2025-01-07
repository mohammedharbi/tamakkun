package com.example.tamakkun.ControllerTest;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.Controller.AuthController;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.bind.annotation.PutMapping;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.time.LocalTime;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AuthController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})

public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @BeforeEach
    void setup() {
    }
    @Test
    @WithMockUser
    public void testVerifyCentre() throws Exception {
        Integer centreId = 1;
        ApiResponse apiResponse = new ApiResponse("Centre has been successfully verified!");
        mockMvc.perform(put("/api/v1/tamakkun-system/auth/centre-verify/{centreId}",centreId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(centreId)))
                .andExpect(status().isOk());        // محاكاة استدعاء الخدمة

    }


    @Test
    @WithMockUser

    public void testGetAllUnverifiedCentres() throws Exception {
        // إعداد البيانات
        Centre centre1 = new Centre(1, "Centre1", "Description1", "Address1", LocalTime.of(8, 0), LocalTime.of(10, 0),
                "12345", "05648724897", false, 50.0, "Image1", null, null, null, null, null, null, null);
        Centre centre2 = new Centre(2, "Centre2", "Description2", "Address2", LocalTime.of(8, 0), LocalTime.of(10, 0),
                "12345", "05648724897", false, 50.0, "Image2", null, null, null, null, null, null, null);

        List<Centre> unverifiedCentres = List.of(centre1, centre2);
        when(authService.getAllUnverifiedCentres()).thenReturn(unverifiedCentres);
        mockMvc.perform(get("/api/v1/tamakkun-system/auth/get-unverified-centres"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(unverifiedCentres.size()))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Centre1"))
                .andExpect(jsonPath("$[0].description").value("Description1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Centre2"))
                .andExpect(jsonPath("$[1].description").value("Description2"));  }
    @Test
    @WithMockUser
    public void testUnActiveParent() throws Exception {
        Integer parentId = 2;
        mockMvc.perform(put("/api/v1/tamakkun-system/auth/un-active-parent/{parent_id}",parentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(parentId)))
                .andExpect(status().isOk());

    }

}
