package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int petId;

    private String name;
    private String type;
    private int age;
    @ElementCollection(targetClass=String.class)
    private Set<String> illnesses;


    @ElementCollection(targetClass=String.class)
    private Set<String> appointments;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinColumn(
            name = "owner ID",
            referencedColumnName = "userId"
    )
    private User owner;

    @JsonIgnore
    @OneToMany(mappedBy = "pet")
    List<Appointment> pets = new ArrayList<>();
}