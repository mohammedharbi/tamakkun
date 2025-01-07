package com.example.tamakkun.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Pattern(regexp = "COMPLAINT|SUGGESTION")
    @NotEmpty(message = "type is required!")
    @Column(columnDefinition = "varchar(30) not null")
    private String type;
    @NotEmpty(message = "description is required!")
    @Column(columnDefinition = "varchar(250) not null")
    private String description;
    @Pattern(regexp = "open|close|in_progress",message = "Invalid status. Allowed values are: OPEN, IN_PROGRESS, CLOSED")
    @NotEmpty(message = "status is required!")
    @Column(columnDefinition = "varchar(30) not null")
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "created date must be Future Or Present")
    @Column(columnDefinition = "datetime")
    private LocalDate createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "handled date must be Future Or Present")
    @Column(columnDefinition = "datetime")
    private LocalDate handledAt;
    //user how send the ticket
    @ManyToOne
    @JsonIgnore
    private MyUser createdBy;
    //if the ticket about Center
    @ManyToOne
    @JsonIgnore
    private Centre centre;
    //if the ticket about post
    @ManyToOne
    @JsonIgnore
    private Post post;
    @ManyToOne
    @JsonIgnore
    private Parent parent;

    @ManyToOne
    @JsonIgnore
    private PostComment postComment;

    @OneToMany(mappedBy = "ticket")
    private Set<TicketComment> comments;



}
