package com.example.tamakkun.Config;

import com.example.tamakkun.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/tamakkun-system/parent/parent-register","/api/v1/tamakkun-system/centre/centre-register","/api/v1/tamakkun-system/activity/get-activities-by-centre/**", "/api/v1/tamakkun-system/activity/activities-by-disabilityType/", "/api/v1/tamakkun-system/activity/description-audio/", "/api/v1/tamakkun-system/centre/get-centres","/api/v1/tamakkun-system/centre/description-audio/").permitAll()
                .requestMatchers("/api/v1/tamakkun-system/ticket/create-complaint-on-post","/api/v1/tamakkun-system/ticket/create-complaint-on-comment/","/api/v1/tamakkun-system/ticket/create-complaint-on-center/","/api/v1/tamakkun-system/ticket/get-all-tickets-by-parent", "/api/v1/tamakkun-system/booking/new-booking/child/","/api/v1/tamakkun-system/post/add-post","/api/v1/tamakkun-system/post/get-Post",
                        "/api/v1/tamakkun-system/post/update/","/api/v1/tamakkun-system/post/delete/","/api/v1/tamakkun-system/post/get-Post-by-id/","/api/v1/tamakkun-system/post/search-by-keyword/","/api/v1/tamakkun-system/post/search-by-date/","/api/v1/tamakkun-system/post/like-post/","/api/v1/tamakkun-system/post/unlike-post/","/api/v1/tamakkun-system/post/get-all-liked-posts","/api/v1/tamakkun-system/post/bookmark-post/",
                        "/api/v1/tamakkun-system/post/remove-bookmark-post/","/api/v1/tamakkun-system/post/get-bookmark-posts","/api/v1/tamakkun-system/post/get-post-by-parent-name/","/api/v1/tamakkun-system/child/get-my-children","/api/v1/tamakkun-system/child/get-child/","/api/v1/tamakkun-system/centre/filter-centres-byPrices/","/api/v1/tamakkun-system/comment/add-comment/",
                        "/api/v1/tamakkun-system/centre/centre-by-name/","/api/v1/tamakkun-system/comment/get-comments-By-Post/", "/api/v1/tamakkun-system/centre/centres-by-address/", "/api/v1/tamakkun-system/child/add-child", "/api/v1/tamakkun-system/centre/centres-by-hours-range/","/api/v1/tamakkun-system/comment/delete/", "/api/v1/tamakkun-system/child/update/", "/api/v1/tamakkun-system/child/delete/","/api/v1/tamakkun-system/comment/get-new-comments-By-Post/","/api/v1/tamakkun-system/comment/update/",
                        "/api/v1/tamakkun-system/parent/get-parent-by-id","/api/v1/tamakkun-system/parent/update","/api/v1/tamakkun-system/parent/delete","/api/v1/tamakkun-system/parent/get-all-new-bookings-by-parent","/api/v1/tamakkun-system/parent/get-all-old-bookings-by-parent","/api/v1/tamakkun-system/parent/get-all-un-reviewed-bookings-by-parent","/api/v1/tamakkun-system/parent/get-all-reviewed-bookings-by-parent","/api/v1/tamakkun-system/parent/recommendation-to-parents-by-centre-and-activities",
                        "/api/v1/tamakkun-system/parent/cancel-booking/booking/", "/api/v1/tamakkun-system/review/new-review/booking/").hasAuthority("PARENT")
                .requestMatchers("/api/v1/tamakkun-system/ticket/create-complaint-on-parent/", "/api/v1/tamakkun-system/activity/add-activity","api/v1/tamakkun-system/activity/update-activity/", "api/v1/tamakkun-system/activity/delete-activity/","/api/v1/tamakkun-system/ticket/get-all-tickets-by-center", "/api/v1/tamakkun-system/booking/mark-scanned/", "/api/v1/tamakkun-system/centre/update-centre/", "/api/v1/tamakkun-system/centre/delete-centre", "/api/v1/tamakkun-system/centre/all-specialists-byCentre",
                        "/api/v1/tamakkun-system/centre/get-myCentre", "/api/v1/tamakkun-system/centre/get-all-old-bookings-by-centre", "/api/v1/tamakkun-system/centre/get-all-new-bookings-by-centre", "/api/v1/tamakkun-system/review/reviews/byCentre", "/api/v1/tamakkun-system/review/top-three-specialists", "/api/v1/tamakkun-system/specialist/add-specialist", "/api/v1/tamakkun-system/specialist/update-specialist/","/api/v1/tamakkun-system/specialist/delete-specialist/", "/api/v1/tamakkun-system/specialist/get-specialist-byName/", "/api/v1/tamakkun-system/specialist/get-specialist-byDisability/").hasAuthority("CENTRE")
                .requestMatchers("/api/v1/tamakkun-system/ticket/change-status/","/api/v1/tamakkun-system/ticket/get-close-tickets","/api/v1/tamakkun-system/ticket/get-open-tickets", "/api/v1/tamakkun-system/auth/centre-verify/", "/api/v1/tamakkun-system/auth/get-unverified-centres", "/api/v1/tamakkun-system/auth/un-active-parent/","/api/v1/tamakkun-system/auth/get-all-un-active-parent/**"
                        ,"/api/v1/tamakkun-system/community/update/", "/api/v1/tamakkun-system/community/delete/", "/api/v1/tamakkun-system/centre/get-all-centres", "/api/v1/tamakkun-system/specialist/get-all-specialists", "api/v1/tamakkun-system/review/get-All-reviews", "/api/v1/tamakkun-system/auth/get-all-users").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/tamakkun-system/ticket-comment/add-comment/","/api/v1/tamakkun-system/ticket-comment/get-comments-by-ticket/","/api/v1/tamakkun-system/community/get-community/").hasAnyAuthority("PARENT","CENTRE","ADMIN")
                .requestMatchers("/api/v1/tamakkun-system/ticket/create-suggestion").hasAnyAuthority("CENTRE","PARENT")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
