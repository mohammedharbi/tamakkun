package com.example.tamakkun.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Title is required!")
    @Column(columnDefinition = "varchar(250) not null")
    private String title;

    @NotEmpty(message = "Content is required!")
    @Column(columnDefinition = "varchar(250) not null")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "created date must be Future Or Present")
    @Column(columnDefinition = "datetime")
    private LocalDate createdAt;

    @Column(columnDefinition = "int")
    private Integer likes=0;

    @ElementCollection
    private Set<Integer> likedBy =new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private Community community;

    @ManyToOne
    @JsonIgnore
    private Parent parent;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "post")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "post")
    private Set<Ticket> tickets;





}
