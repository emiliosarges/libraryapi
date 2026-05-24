package com.souemilio.libraryapi.repository;

import com.souemilio.libraryapi.model.Autor;
import com.souemilio.libraryapi.model.Livro;
import com.souemilio.libraryapi.projection.LivroAutorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

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
}