package com.capgemini.polytech.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "boite")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false)
    private int quantite;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, length = 100)
    private String pointGeo;

    @OneToMany(mappedBy="boite")
    private List<Reservation> reservations;
}


