package com.example.tamakkun.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "Name is required!")
    @Column(columnDefinition = "varchar(30) not null")
    private String fullName;

    @NotEmpty(message = "PhoneNumber is required!")
    @Pattern(regexp = "^(\\+966|0)?5\\d{8}$",   message = "Phone number must start with +966 or 05 and be followed by 8 digits")
    @Column(columnDefinition = "varchar(13) not null")
    private String phoneNumber;

    @NotEmpty(message = "Address is required!")
    @Column(columnDefinition = "varchar(50) not null")
    private String address;

    private Boolean isActive=true;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent")
    private Set<Child> children;

    @ManyToMany(mappedBy = "parents")
    private Set<Community> communities;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent")
    private Set<Post> posts;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent")
    private Set<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent")
    private Set<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent")
    private Set<Booking> bookings;





}
