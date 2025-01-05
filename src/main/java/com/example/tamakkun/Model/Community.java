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

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "FullName is required!")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotEmpty(message = "Full name is required!")
    @Column(columnDefinition = "varchar(800) not null")
    private String description;

    @NotEmpty(message = "Full name is required!")
    @Column(columnDefinition = "varchar(800) not null")
    private String rules;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "created date must be Future Or Present")
    @Column(columnDefinition = "datetime")
    private LocalDateTime createdAt;

    @ManyToMany
    @JsonIgnore
    private Set<Parent> parents;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "community")
    private Set<Post> posts;


}
