package com.capgemini.polytech.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.capgemini.polytech.dto.ReservationDto;
import com.capgemini.polytech.dto.UtilisateurCreateDto;
import com.capgemini.polytech.dto.UtilisateurDto;
import com.capgemini.polytech.dto.UtilisateurReservationResponseDto;
import com.capgemini.polytech.mappers.UtilisateurMapper;
import com.capgemini.polytech.models.Role;
import com.capgemini.polytech.models.Utilisateur;
import com.capgemini.polytech.repositories.RoleRepository;
import com.capgemini.polytech.repositories.UtilisateurRepository;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UtilisateurMapper utilisateurMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UtilisateurDto getUtilisateurById(Integer id) {
        return utilisateurRepository.findById(id)
                .map(utilisateurMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found"));
    }

    public List<UtilisateurDto> getAllUtilisateurs() {
        return utilisateurMapper.toDTO(utilisateurRepository.findAll());
    }

    public UtilisateurDto createUtilisateur(UtilisateurCreateDto utilisateurcreated) {

        if (utilisateurRepository.existsById(utilisateurcreated.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Utilisateur already exists");
        }
        try {
            Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurcreated);
            utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));

            // Fetch roles from the database by their IDs (or names)
            Set<Role> roles = new HashSet<>();
            for (String role_name : utilisateurcreated.getRole()) { // Assuming roleIds is a List<Long> in the DTO
                Role role = roleRepository.findByName(role_name)
                        .orElseGet(() -> { // If role not found, create it
                            Role newRole = new Role();
                            newRole.setName(role_name); // Set the name from the input
                            return roleRepository.save(newRole); // Save and return the newly created role
                        });
                roles.add(role);
            }

            // Assign the roles to the user
            utilisateur.setRoles(roles);

            Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
            return utilisateurMapper.toDTO(savedUtilisateur);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create utilisateur", e);
        }
    }

    public UtilisateurDto updateUtilisateur(UtilisateurDto utilisateurDTO) {
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDTO);
        // Vérification si les rôles sont nuls ou vides
    Set<Role> roles = new HashSet<>();
    if (utilisateurDTO.getRoles() != null) {
        for (Role role_name : utilisateurDTO.getRoles()) {
            Role role = roleRepository.findByName(role_name.getName())
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(role_name.getName());
                        return roleRepository.save(newRole);
                    });
            roles.add(role);
        }
    }

        return utilisateurRepository.findById(utilisateur.getId())
                .map(existingUtilisateur -> {
                    existingUtilisateur.setNom(utilisateur.getNom());
                    existingUtilisateur.setPrenom(utilisateur.getPrenom());
                    existingUtilisateur.setMail(utilisateur.getMail());
                    existingUtilisateur.setUsername(utilisateur.getUsername());
                    existingUtilisateur.setRoles(utilisateur.getRoles());
                    Utilisateur savedUtilisateur = utilisateurRepository.save(existingUtilisateur);
                    return utilisateurMapper.toDTO(savedUtilisateur);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found"));
    }

    public UtilisateurDto updateUtilisateurPassword(Integer id, String password) {
        return utilisateurRepository.findById(id)
                .map(existingUtilisateur -> {
                    existingUtilisateur.setPassword(passwordEncoder.encode(password));
                    Utilisateur savedUtilisateur = utilisateurRepository.save(existingUtilisateur);
                    return utilisateurMapper.toDTO(savedUtilisateur);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found"));
    }

    public void deleteUtilisateur(Integer id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found");
        }
        utilisateurRepository.deleteById(id);
    }

    public UtilisateurReservationResponseDto createUtilisateurReservationResponse(UtilisateurDto utilisateur,
            List<ReservationDto> reservationDTOs) {
        return new UtilisateurReservationResponseDto(utilisateur, reservationDTOs);
    }

}
