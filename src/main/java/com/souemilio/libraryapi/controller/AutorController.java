package com.souemilio.libraryapi.controller;

import com.souemilio.libraryapi.controller.dto.AutorDTO;
import com.souemilio.libraryapi.controller.dto.ErroResposta;
import com.souemilio.libraryapi.controller.mappers.AutorMapper;
import com.souemilio.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.souemilio.libraryapi.exceptions.RegistroDuplicadoException;
import com.souemilio.libraryapi.model.Autor;
import com.souemilio.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
public class AutorController implements GenericController{

    private final AutorService autorService;
    private final AutorMapper mapper;

    public AutorController(
            AutorService autorService,
            AutorMapper mapper
    ) {
        this.autorService = autorService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
            Autor autor = mapper.toEntity(dto);
            autorService.salvar(autor);
            URI location = gerarHeaderLocation(autor.getId());
            return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable String id) {

        var idAutor = UUID.fromString(id);

        return autorService
                .buscarAutorPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDto(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {

            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.buscarAutorPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            autorService.deletar(autorOptional.get());

            return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisaByExemple(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(lista);

    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String id, @RequestBody AutorDTO dto) {

            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.buscarAutorPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            autorService.atualizar(autor);

            return ResponseEntity.noContent().build();

    }
}
