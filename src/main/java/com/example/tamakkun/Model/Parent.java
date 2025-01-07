package com.example.tamakkun.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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


   // @NotEmpty(message = "Name is required!")
    @Column(columnDefinition = "varchar(30) not null")
    private String fullName;

    //@NotEmpty(message = "PhoneNumber is required!")
    //@Pattern(regexp = "^(\\+966|0)?5\\d{8}$",   message = "Phone number must start with +966 or 05 and be followed by 8 digits")
    @Column(columnDefinition = "varchar(13) not null unique")
    private String phoneNumber;

   // @NotEmpty(message = "Address is required!")
    @Column(columnDefinition = "varchar(50) not null")
    private String address;

    private Boolean isActive=true;

    @ElementCollection
    private Set<Integer> bookmarkedPostIds = new HashSet<>();

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
    private Set<PostComment> postComments;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent")
    private Set<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent")
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "parent")
    private Set<Ticket> tickets;



}
