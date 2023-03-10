package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private String name;

    @ManyToMany(
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST}
    )
    @JoinTable(
            name = "vets_specialization",
            joinColumns = @JoinColumn(name = "specialization_Id"),
            inverseJoinColumns = @JoinColumn(name = "vet_Id")
    )
    public List<Vet> listOfVets = new ArrayList<>();
}