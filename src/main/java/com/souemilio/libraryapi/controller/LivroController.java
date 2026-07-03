package com.souemilio.libraryapi.controller;

import com.souemilio.libraryapi.controller.dto.CadastroLivroDTO;
import com.souemilio.libraryapi.controller.dto.ErroResposta;
import com.souemilio.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.souemilio.libraryapi.controller.mappers.LivroMapper;
import com.souemilio.libraryapi.exceptions.RegistroDuplicadoException;
import com.souemilio.libraryapi.model.Livro;
import com.souemilio.libraryapi.repository.LivroRepository;
import com.souemilio.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }
    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
