package com.capgemini.polytech.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoiteDto {

    private int id;
    private String nom;
    private int quantite;
    private String description;
    private String pointGeo;
}
