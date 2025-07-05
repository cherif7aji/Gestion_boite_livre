package com.capgemini.polytech.models;

import lombok.Data;
import java.io.Serializable;

@Data
public class ReservationId implements Serializable {

    private int utilisateur;
    private int boite;
}
