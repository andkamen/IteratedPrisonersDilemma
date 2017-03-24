package com.ipdweb.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "simulations")
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String name;

    @OneToMany(mappedBy = "simulation", cascade = CascadeType.ALL)
    List<Generation> generations;



}
