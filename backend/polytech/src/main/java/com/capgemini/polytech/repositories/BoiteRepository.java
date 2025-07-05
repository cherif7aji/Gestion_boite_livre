package com.capgemini.polytech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.capgemini.polytech.models.Boite;



@Repository
public interface BoiteRepository extends JpaRepository<Boite,Integer>  {
    
}
