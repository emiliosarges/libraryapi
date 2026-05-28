package com.souemilio.libraryapi.repository;

import com.souemilio.libraryapi.model.Autor;
import com.souemilio.libraryapi.model.GeneroLivro;
import com.souemilio.libraryapi.model.Livro;
import com.souemilio.libraryapi.projection.LivroAutorProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    @Query(value = """
    SELECT
        a.nome AS nome,
        l.titulo AS titulo
    FROM livro l
    JOIN autor a
        ON a.id = l.id_autor
    """, nativeQuery = true)
    List<LivroAutorProjection> findByAutorAndTitulo();

    //QUery Method
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn);

    List<Livro> findByDataPublicacaoBetween(LocalDate dataInicio, LocalDate dataFim);

    // JPQL -> referencia as entidades e as propriedades
    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    @Query(" select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    //select distinct l.* from livro l;
    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
        select l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'Mexicano'
        order by l.genero
    """)
    List<String> listarGenerosAutoresMexicanos();

    //named parameters -> parametros nomeados
    @Query("select l from Livro l where l.genero = :genero order by :paramOdenacao")
    List<Livro> findByGenero(
            @Param("genero")GeneroLivro  generoLivro,
            @Param("paramOdenacao")String paramOdenacao);

    //Positional parameters
    @Query("select l from Livro l where l.genero = ?1 order by ?2 ")
    List<Livro> findByGeneroPositionalParameters(
            GeneroLivro  generoLivro,
            String paramOdenacao);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1 ")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1 ")
    void updateDataPublicacao(LocalDate novaData);

}