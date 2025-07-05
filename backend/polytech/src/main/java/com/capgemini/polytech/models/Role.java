package com.capgemini.polytech.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name; // This maps to the authority name (e.g., "ROLE_ADMIN")

    public Role(String name) {
        this.name = name;
    }

    public Role(int id,String name) {
        this.id=id;
        this.name = name;
    }
}
