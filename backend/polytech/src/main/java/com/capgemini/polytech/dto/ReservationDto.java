package com.capgemini.polytech.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.capgemini.polytech.models.ReservationId;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {


    private ReservationId id;
    @Column(name="utilisateur")
    private UtilisateurDto utilisateur;
    @Column(name="boite")
    private BoiteDto boite;
    @Column(nullable = false, name = "reservation")
    private int reservation;
}
