package com.capgemini.polytech.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.capgemini.polytech.dto.BoiteDto;
import com.capgemini.polytech.dto.BoiteReservationResponseDto;
import com.capgemini.polytech.dto.ReservationDto;
import com.capgemini.polytech.mappers.BoiteMapper;
import com.capgemini.polytech.models.Boite;
import com.capgemini.polytech.repositories.BoiteRepository;

@Service
public class BoiteService {

    @Autowired
    private BoiteRepository boiteRepository;

    @Autowired
    private BoiteMapper boiteMapper;

    public List<BoiteDto> getAllBoites() {
        return boiteMapper.toDTO(boiteRepository.findAll());
    }

    public BoiteDto getBoiteById(Integer id) {
        return boiteRepository.findById(id)
                .map(boiteMapper::toDTO).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Boite not found"));
    }

    public BoiteDto createBoite(BoiteDto boiteDTO) {
        if (boiteRepository.existsById(boiteDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Utilisateur already exists");
        }
        Boite boite = boiteMapper.toEntity(boiteDTO);
        Boite savedBoite = boiteRepository.save(boite);
        return boiteMapper.toDTO(savedBoite);
    }

    public BoiteDto updateBoite(BoiteDto boiteDTO) {
        Boite boite = boiteMapper.toEntity(boiteDTO);
        return boiteRepository.findById(boite.getId())
                .map(existingBoite -> {
                    existingBoite.setNom(boite.getNom());
                    existingBoite.setDescription(boite.getDescription());
                    existingBoite.setPointGeo(boite.getPointGeo());
                    existingBoite.setQuantite(boite.getQuantite());
                    return boiteMapper.toDTO(boiteRepository.save(existingBoite));
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Boite not found"));
    }

    public void deleteBoite(Integer id) {
        if (!boiteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boite not found");

        }
        boiteRepository.deleteById(id);

    }


    public BoiteReservationResponseDto createBoiteReservationResponse(BoiteDto boite,
            List<ReservationDto> reservationDTOs) {
        return new BoiteReservationResponseDto(boite, reservationDTOs);
    }
}
