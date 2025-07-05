package com.capgemini.polytech.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.polytech.dto.ReservationDto;
import com.capgemini.polytech.dto.UtilisateurCreateDto;
import com.capgemini.polytech.dto.UtilisateurDto;
import com.capgemini.polytech.dto.UtilisateurReservationResponseDto;
import com.capgemini.polytech.services.ReservationService;
import com.capgemini.polytech.services.UtilisateurService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/utilisateurs/")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("{id}")
    public UtilisateurDto getUtilisateurById(@PathVariable Integer id) {
        UtilisateurDto utilisateur = utilisateurService.getUtilisateurById(id);
        return utilisateur;
    }

    @GetMapping("all")
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateurs() {
        List<UtilisateurDto> utilisateurs = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("allreservations/{id}")
    public ResponseEntity<UtilisateurReservationResponseDto> getAllUtilisateurReservations(@PathVariable Integer id) {
        UtilisateurDto utilisateur = utilisateurService.getUtilisateurById(id);
        List<ReservationDto> reservations = reservationService.getAllReservationsByUser(id);
        UtilisateurReservationResponseDto response = utilisateurService
                .createUtilisateurReservationResponse(utilisateur, reservations);
        return ResponseEntity.ok(response);
    }

    @PostMapping("create")
    public ResponseEntity<UtilisateurDto> createUtilisateur(@RequestBody UtilisateurCreateDto utilisateur) {
        UtilisateurDto createdUtilisateur = utilisateurService.createUtilisateur(utilisateur);
        return ResponseEntity.ok(createdUtilisateur);
    }

    // Endpoint to update utilisateur details
    @PutMapping("update/{id}")
    public ResponseEntity<UtilisateurDto> updateUtilisateur(
            @PathVariable Integer id,
            @RequestBody UtilisateurDto utilisateurDTO) {
        utilisateurDTO.setId(id); // Assurez-vous que l'ID est défini dans le DTO
        UtilisateurDto updatedUtilisateur = utilisateurService.updateUtilisateur(utilisateurDTO);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    // Endpoint to update utilisateur password
    @PatchMapping("update-password/{id}")
    public ResponseEntity<UtilisateurDto> updateUtilisateurPassword(@PathVariable Integer id,
            @RequestBody String password) {
        UtilisateurDto updatedUtilisateur = utilisateurService.updateUtilisateurPassword(id, password);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    // Endpoint to delete a utilisateur by ID
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Integer id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

}
