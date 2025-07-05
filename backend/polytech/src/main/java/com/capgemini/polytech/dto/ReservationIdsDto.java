package com.capgemini.polytech.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationIdsDto {


    @Column(name="utilisateur")
    private int utilisateur;
    @Column(name="boite")
    private int boite;
    @Column(nullable = false, name = "reservation")
    private int reservation;
}
