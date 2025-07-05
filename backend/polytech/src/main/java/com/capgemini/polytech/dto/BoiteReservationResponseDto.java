package com.capgemini.polytech.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class BoiteReservationResponseDto {
    
    private Integer id;
    private String nom;
    private Integer quantite;
    private String pointGeo;
    private String description;
    private List<ReservationDto> reservations;

    public BoiteReservationResponseDto (BoiteDto boiteDTO, List<ReservationDto> reservationDTOs){
            super();
            this.id=boiteDTO.getId();
            this.nom=boiteDTO.getNom();
            this.description=boiteDTO.getDescription();
            this.quantite=boiteDTO.getQuantite();
            this.pointGeo=boiteDTO.getPointGeo();
            this.reservations=reservationDTOs;
    }
}
