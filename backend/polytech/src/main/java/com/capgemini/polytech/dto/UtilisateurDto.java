package com.capgemini.polytech.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

import com.capgemini.polytech.models.Role;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {

    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String username;
    private Set<Role> roles;
}
