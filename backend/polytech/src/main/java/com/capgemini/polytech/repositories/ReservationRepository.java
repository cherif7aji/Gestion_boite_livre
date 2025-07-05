package com.capgemini.polytech.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.capgemini.polytech.models.Reservation;
import com.capgemini.polytech.models.ReservationId;


@Repository
public interface ReservationRepository extends JpaRepository <Reservation,ReservationId>{

    List<Reservation> findAllByUtilisateurId(Integer utilisateurId);
    List<Reservation> findAllByBoiteId(Integer boiteId);
}
