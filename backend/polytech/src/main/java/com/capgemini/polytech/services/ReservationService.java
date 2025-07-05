package com.capgemini.polytech.services;

import com.capgemini.polytech.models.Boite;
import com.capgemini.polytech.models.Reservation;
import com.capgemini.polytech.models.ReservationId;
import com.capgemini.polytech.models.Utilisateur;
import com.capgemini.polytech.dto.BoiteDto;
import com.capgemini.polytech.dto.ReservationDto;
import com.capgemini.polytech.dto.ReservationIdsDto;

import com.capgemini.polytech.mappers.BoiteMapper;
import com.capgemini.polytech.mappers.ReservationMapper;
import com.capgemini.polytech.mappers.UtilisateurMapper;
import com.capgemini.polytech.repositories.BoiteRepository;
import com.capgemini.polytech.repositories.ReservationRepository;
import com.capgemini.polytech.repositories.UtilisateurRepository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private BoiteRepository boiteRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private UtilisateurMapper utilisateurMapper;

    @Autowired
    private BoiteService boiteService;

    @Autowired
    private BoiteMapper boiteMapper;

    public List<ReservationDto> getAllReservations() {
        return reservationMapper.toDTO(reservationRepository.findAll());
    }

    public List<ReservationDto> getAllReservationsByUser(Integer userId) {
        if (!utilisateurRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found");
        }
        return reservationRepository.findAllByUtilisateurId(userId)
                .stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDto> getAllReservationsByBoite(Integer boiteId) {
        if (!boiteRepository.existsById(boiteId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boite not found");
        }
        return reservationRepository.findAllByBoiteId(boiteId)
                .stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public ReservationDto getReservationById(ReservationId reservationId) {
        return reservationRepository.findById(reservationId)
                .map(reservationMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    public ReservationDto convertReservationIdsDTOtoReservationDTO(ReservationIdsDto reservationIdsDTO) {
        Utilisateur utilisateur = utilisateurRepository.findById(reservationIdsDTO.getUtilisateur())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found"));
        Boite boite = boiteRepository.findById(reservationIdsDTO.getBoite())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Boite not found"));

        ReservationDto reservationDTO = new ReservationDto();
        reservationDTO.setUtilisateur(utilisateurMapper.toDTO(utilisateur));
        reservationDTO.setBoite(boiteMapper.toDTO(boite));
        reservationDTO.setReservation(reservationIdsDTO.getReservation());
        return reservationDTO;
    }

    public ReservationDto createReservation(ReservationDto reservationDTO) {

        ReservationId reservationId = new ReservationId();
        reservationId.setBoite(reservationDTO.getBoite().getId());
        reservationId.setUtilisateur(reservationDTO.getUtilisateur().getId());

        if (reservationRepository.existsById(reservationId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Reservation already exists");
        }

        Utilisateur utilisateur = utilisateurRepository.findById(reservationId.getUtilisateur())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Utilisateur not found"));
        Boite boite = boiteRepository.findById(reservationId.getBoite())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Boite not found"));
   


        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation.setId(reservationId);
        reservation.setUtilisateur(utilisateur);
        reservation.setBoite(boite);

        reservationRepository.save(reservation);

        return reservationMapper.toDTO(reservation);
    }

    public ReservationDto reserveBoite(ReservationIdsDto reservationIdsDto) {
        ReservationDto reservationDto = convertReservationIdsDTOtoReservationDTO(reservationIdsDto);

        // Vérifier la quantité
        BoiteDto boite = reservationDto.getBoite();
        if (boite.getQuantite() < reservationDto.getReservation()) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,"Quantité insuffisante dans la boîte.");
        }

        // Mettre à jour la quantité
        boite.setQuantite(boite.getQuantite() - reservationDto.getReservation());
        boiteService.updateBoite(boite);

        // Créer la réservation
        ReservationDto createdReservation = createReservation(reservationDto);

        return createdReservation;
    }

    public void rendreLivres(ReservationIdsDto reservationDto){
        BoiteDto boite = convertReservationIdsDTOtoReservationDTO(reservationDto).getBoite();
        boite.setQuantite(boite.getQuantite() + reservationDto.getReservation());
        boiteService.updateBoite(boite);
        System.out.println("fuck");
        ReservationId id = new ReservationId();
        id.setBoite(boite.getId());
        id.setUtilisateur(reservationDto.getUtilisateur());
        if (!reservationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        }
        reservationRepository.deleteById(id);
    }


    public ReservationDto updateReservation(ReservationDto reservationDTO) {
        ReservationId id = new ReservationId();
        id.setBoite(reservationDTO.getBoite().getId());
        id.setUtilisateur(reservationDTO.getUtilisateur().getId());
        return reservationRepository.findById(id)
                .map(existingReservation -> {
                    existingReservation.setReservation(reservationDTO.getReservation());
                    Reservation updatedReservation = reservationRepository.save(existingReservation);
                    return reservationMapper.toDTO(updatedReservation);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    public void deleteReservation(ReservationId id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        }
        reservationRepository.deleteById(id);
    }
}
