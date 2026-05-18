package com.souemilio.libraryapi.repository;

import com.souemilio.libraryapi.model.Autor;
import com.souemilio.libraryapi.model.GeneroLivro;
import com.souemilio.libraryapi.model.Livro;
import com.souemilio.libraryapi.projection.LivroAutorProjection;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {
    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("123");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository
                .findById(UUID.fromString("16e6bde5-0002-4eb4-a8bc-0935f9123ef8"))
                .orElse(null);

        livro.setAutor(new Autor());
        livroRepository.save(livro);
    }

    @Test
        //Aqui funciona sem o cascade
    void salvarAutorELivroTest() {
        Livro livro = new Livro();
        livro.setIsbn("456");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(2000, 5, 17));

        Autor autor = new Autor();
        autor.setNome("Rodolfo");
        autor.setNacionalidade("Indiano");
        autor.setDataNascimento(LocalDate.of(1888, 1, 20));

        autorRepository.save(autor);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("456");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(2000, 5, 17));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1888, 1, 20));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void buscarPorAutorETitulo() {

        List<LivroAutorProjection> resultado =
                livroRepository.findByAutorAndTitulo();

        resultado.forEach(item ->
                System.out.println(
                        item.getNome() + " - " + item.getTitulo()
                )
        );
    }

    @Test
    void atualizarAutorDoLivroTest() {
        var idLivro = UUID.fromString("19333e97-1959-45a4-b272-d421b62b3c2e");
        var livroParaAtualizar = livroRepository
                .findById(idLivro)
                .orElse(null);

        var idAutor = UUID.fromString("be34de2c-ffd2-43db-9511-698a5d38cad5");
        var autorParaAtualizar = autorRepository
                .findById(idAutor)
                .orElse(null);

        if (livroParaAtualizar != null) {
            livroParaAtualizar.setAutor(autorParaAtualizar);
        }

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deletarTest() {

    var idLivro = UUID.fromString("46fe76f1-0b7d-4779-b871-95897e910fbe");
    var livroParaAtualizar = livroRepository
            .findById(idLivro)
            .orElse(null);
    livroRepository.delete(livroParaAtualizar);
    }

    @Test
    @Transactional  //LAZY - Busca os dados do autor caso nescessário.
    void buscarLivroTest() {
        UUID idLivro = UUID.fromString("19333e97-1959-45a4-b272-d421b62b3c2e");
        Livro livro = livroRepository.findById(idLivro).orElse(null);

        System.out.println("LIVRO");
        System.out.println(livro.getTitulo());

        System.out.println("AUTOR");
        System.out.println(livro.getAutor().getNome());
    }
}
