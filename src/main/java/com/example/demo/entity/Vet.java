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
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int vetId;

    private String name;
    private String surname;
    private String address;
    private int phone;
    private String email;
    private long PESEL;
    private int price;

    @JsonIgnore
    @OneToMany(mappedBy = "vet")
    List<Appointment> appointments = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER
            ,mappedBy = "listOfVets"
            ,cascade = CascadeType.ALL)
    private List<Specialization> specializations = new ArrayList<>();
}