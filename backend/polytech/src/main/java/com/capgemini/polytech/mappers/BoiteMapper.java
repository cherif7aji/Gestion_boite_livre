package com.capgemini.polytech.mappers;
import java.util.List;

import org.mapstruct.Mapper;

import com.capgemini.polytech.dto.BoiteDto;
import com.capgemini.polytech.models.Boite;



@Mapper(componentModel = "spring")
public interface BoiteMapper {

    BoiteDto toDTO(Boite BoiteDto);
    
    List<BoiteDto> toDTO(List<Boite> Boites);

    Boite toEntity(BoiteDto Boite);
    
    
} 
