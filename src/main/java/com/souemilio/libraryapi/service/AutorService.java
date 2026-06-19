package com.souemilio.libraryapi.service;

import com.souemilio.libraryapi.exceptions.OperacaoNaoPemitidaException;
import com.souemilio.libraryapi.model.Autor;
import com.souemilio.libraryapi.repository.AutorRepository;
import com.souemilio.libraryapi.repository.LivroRepository;
import com.souemilio.libraryapi.validator.AutorValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    //Quando uso @AllArgsConstructor do Lombok, posso eliminar o construtor
    //Só para nivel de conhecimento, pois no projeto atual que trabalho
    //não usamos lombok, mas sei usar caso preciso.
//    public AutorService(AutorRepository autorRepository, AutorValidator validator, LivroRepository livroRepository) {
//        this.autorRepository = autorRepository;
//        this.validator = validator;
//        this.livroRepository = livroRepository;
//    }

    public Autor salvar(Autor autor) {
        validator.validar(autor);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if(autor.getId() == null) {
            throw new IllegalArgumentException("Autor não encontrado na base de dados");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> buscarAutorPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor) {
        if(possuiLivro(autor)){
            throw new OperacaoNaoPemitidaException(
                    "Não permitido excluir: Autor possui livros cadastrados!"
            );
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if(nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nome != null) {
            return autorRepository.findByNome(nome);
        }

        if(nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public List<Autor> pesquisaByExemple(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);
        return autorRepository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
