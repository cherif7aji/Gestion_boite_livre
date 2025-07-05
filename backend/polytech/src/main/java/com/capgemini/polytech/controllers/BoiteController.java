package com.capgemini.polytech.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.capgemini.polytech.dto.BoiteDto;
import com.capgemini.polytech.dto.BoiteReservationResponseDto;
import com.capgemini.polytech.dto.ReservationDto;
import com.capgemini.polytech.dto.UtilisateurDto;
import com.capgemini.polytech.dto.UtilisateurReservationResponseDto;
import com.capgemini.polytech.services.BoiteService;
import com.capgemini.polytech.services.ReservationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/boites/")
@CrossOrigin(origins = "http://localhost:4200") 
public class BoiteController {

    @Autowired
    private BoiteService boiteService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("{id}")
    public ResponseEntity<BoiteDto> getBoiteById(@PathVariable Integer id) {
        BoiteDto boite = boiteService.getBoiteById(id);
        return ResponseEntity.ok(boite);
    }

    @GetMapping("all")
    public ResponseEntity<List<BoiteDto>> getAllUtilisateurs() {
        List<BoiteDto> utilisateurs = boiteService.getAllBoites();
        return ResponseEntity.ok(utilisateurs);
    }

    @PostMapping("create")
    public ResponseEntity<BoiteDto> createBoite(@RequestBody BoiteDto boiteDTO) {
        System.out.println("tahhhhherr");
        System.out.println(boiteDTO);
        BoiteDto createdBoite = boiteService.createBoite(boiteDTO);
        return ResponseEntity.ok(createdBoite);
    }

    @PutMapping("update")
    public ResponseEntity<BoiteDto> updateBoite(@RequestBody BoiteDto boiteDTO) {
        BoiteDto updatedBoite = boiteService.updateBoite(boiteDTO);
        return ResponseEntity.ok(updatedBoite);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteBoite(@PathVariable Integer id) {
        boiteService.deleteBoite(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("allreservations/{id}")
    public ResponseEntity<BoiteReservationResponseDto> getAllUtilisateurReservations(@PathVariable Integer id) {
        BoiteDto boite = boiteService.getBoiteById(id);
        List<ReservationDto> reservations = reservationService.getAllReservationsByBoite(id);
        BoiteReservationResponseDto response = boiteService.createBoiteReservationResponse(boite, reservations);
        return ResponseEntity.ok(response);
    }
}
