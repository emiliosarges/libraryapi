package com.souemilio.libraryapi.controller;

import com.souemilio.libraryapi.controller.dto.CadastroLivroDTO;
import com.souemilio.libraryapi.controller.dto.ErroResposta;
import com.souemilio.libraryapi.exceptions.RegistroDuplicadoException;
import com.souemilio.libraryapi.repository.LivroRepository;
import com.souemilio.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            //mapear dto para entidade
            //enviar entidade pare o service validar e salvar na base
            //criar url para acesso dos dados do livro
            //retornar a respostar codigo created com header location

            return ResponseEntity.ok(dto);
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

}
