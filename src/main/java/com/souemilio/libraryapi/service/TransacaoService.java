package com.souemilio.libraryapi.service;

import com.souemilio.libraryapi.model.Autor;
import com.souemilio.libraryapi.model.GeneroLivro;
import com.souemilio.libraryapi.model.Livro;
import com.souemilio.libraryapi.repository.AutorRepository;
import com.souemilio.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void atualiacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("6d850d10-ca0e-4ab2-83d0-e95739ea2711"))
                .orElse(null);
        livro.setDataPublicacao(LocalDate.of(2026,5,29));
    }

    @Transactional
    public void executar(){

        Autor autor = new Autor();
        autor.setNome("Francisca III");
        autor.setNacionalidade("Uruguaio");
        autor.setDataNascimento(LocalDate.of(1966, 1, 5));

        autorRepository.saveAndFlush(autor);



        Livro livro = new Livro();
        livro.setIsbn("44585-55544");
        livro.setPreco(BigDecimal.valueOf(115));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Livro da Francisca III");
        livro.setDataPublicacao(LocalDate.of(1981, 05, 24));

        livro.setAutor(autor);

        livroRepository.saveAndFlush(livro);

        if(autor.getNome().equals("Francisca III")){
            throw new RuntimeException("Rollback");
        }

    }
}
