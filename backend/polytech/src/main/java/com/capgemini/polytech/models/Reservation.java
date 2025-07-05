package com.capgemini.polytech.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @EmbeddedId
    private ReservationId id;

    @ManyToOne
    @MapsId("utilisateur")
    @JoinColumn(name = "utilisateur", referencedColumnName = "id")
    private Utilisateur utilisateur;

    @ManyToOne
    @MapsId("boite")
    @JoinColumn(name = "boite", referencedColumnName = "id")
    private Boite boite;

    @Column(nullable = false)
    private int reservation;
}
