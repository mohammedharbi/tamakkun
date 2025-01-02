package com.example.tamakkun.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MyUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @NotEmpty(message = "Username is required!")
//    @Size(min = 4, max = 10, message = "username must be between 4 and 10")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String username;

    //    @NotEmpty(message = "Password is required!")
//    @Size(min = 6, max = 20, message = "password must be between 6 and 20")
    @Column(columnDefinition = "varchar(9999) not null")
    private String password;

    //    @NotEmpty(message = "Role is required!")
//    @Pattern(regexp = ("^USER|ADMIN$"))
//    @Size(min = 1, max = 10, message = "role must be between 1 min and 10 max")
//    @Column(columnDefinition = "varchar(10)")
    private String role;

    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Parent parent;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Centre centre;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
