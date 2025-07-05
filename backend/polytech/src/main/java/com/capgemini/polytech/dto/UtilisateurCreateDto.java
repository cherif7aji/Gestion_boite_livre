package com.capgemini.polytech.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurCreateDto {
    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String username;
    private String password;
    private List<String> role;
    
        // Getters et Setters
    }

