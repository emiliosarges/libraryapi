package com.souemilio.libraryapi.controller.mappers;

import com.souemilio.libraryapi.controller.dto.AutorDTO;
import com.souemilio.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDto(Autor autor);
}
