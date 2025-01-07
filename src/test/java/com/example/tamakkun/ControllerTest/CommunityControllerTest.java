package com.example.tamakkun.ControllerTest;

import com.example.tamakkun.Controller.CommunityController;
import com.example.tamakkun.DTO_Out.CommunityDTO_out;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.Community;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Parent;
import com.example.tamakkun.Service.CommunityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommunityController.class)
public class CommunityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommunityService communityService;

    @Test
    @WithMockUser
    public void testGetCommunityWithCustomUser() throws Exception {
        MyUser customUser = new MyUser(1, "testUser", "12345", "USER", "test@example.com", null, null, null, null);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(customUser, null, List.of(new SimpleGrantedAuthority("ROLE_USER")))
        );

        Integer communityId = 1;
        CommunityDTO_out communityDTO = new CommunityDTO_out(
                "Community Name",
                "Community Description",
                "Community Rules",
                LocalDateTime.now()
        );

        when(communityService.getCommunity(Mockito.eq(customUser.getId()), Mockito.eq(communityId))).thenReturn(communityDTO);
        mockMvc.perform(get("/api/v1/tamakkun-system/community/get-community/{community_id}", communityId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Community Name"))
                .andExpect(jsonPath("$.description").value("Community Description"))
                .andExpect(jsonPath("$.rules").value("Community Rules"))
                .andExpect(jsonPath("$.createdAt").exists());    }


    @Test
    @WithMockUser
    public void testUpdateCommunity() throws Exception {
        // إعداد المستخدم المخصص مع دور ADMIN
        MyUser adminUser = new MyUser(1, "adminUser", "12345", "ADMIN", "admin@example.com", null, null, null, null);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(adminUser, null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
        );
        Integer communityId = 1;
        String communityJson = """
        {
            "id": 1,
            "name": "Updated Name",
            "description": "Updated Description",
            "rules": "Updated Rules"        }
        """;

        Mockito.doNothing().when(communityService).update(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Community.class));
        mockMvc.perform(put("/api/v1/tamakkun-system/community/update/{community_id}", communityId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(communityJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("updated successfully"));
    }

    @Test
    @WithMockUser
    public void testDeleteCommunityAsAdmin() throws Exception {
        MyUser adminUser = new MyUser(1, "adminUser", "12345", "ADMIN", "admin@example.com", null, null, null, null);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(adminUser, null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
        );

        Integer communityId = 1;
        Mockito.doNothing().when(communityService).delete(adminUser.getId(), communityId);
        mockMvc.perform(delete("/api/v1/tamakkun-system/community/delete/{community_id}",adminUser.getId(), communityId)
                        .with(csrf())) // إضافة CSRF Token
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("deleted successfully"));
    }
}