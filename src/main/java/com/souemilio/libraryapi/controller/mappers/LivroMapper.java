package com.souemilio.libraryapi.controller.mappers;

import com.souemilio.libraryapi.controller.dto.AutorDTO;
import com.souemilio.libraryapi.controller.dto.CadastroLivroDTO;
import com.souemilio.libraryapi.model.Autor;
import com.souemilio.libraryapi.model.Livro;
import com.souemilio.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);


}
