package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private String name;
    private String type;
    private int age;
    @ElementCollection(targetClass=Integer.class)
    private Set<String> illnesses;
    @ElementCollection(targetClass=Integer.class)
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
}