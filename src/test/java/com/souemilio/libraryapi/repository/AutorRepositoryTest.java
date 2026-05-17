package com.souemilio.libraryapi.repository;

import com.souemilio.libraryapi.model.Autor;
import com.souemilio.libraryapi.model.Livro;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

//    @Test
//    public void salvarTest(){
//        Autor autor = new Autor();
//        autor.setNome("Maria");
//        autor.setNacionalidade("Americano");
//        autor.setDataNascimento(LocalDate.of(1951, 9, 8));
//
//        Autor autorSalvo = repository.save(autor);
//        System.out.println("Autor salvo: " + autorSalvo);
//    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("4fe35f75-7ba1-41d3-8e2a-7d484403c300");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1998,12,25));
            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> autores = repository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de Autores "  + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("4fe35f75-7ba1-41d3-8e2a-7d484403c300");
        repository.deleteById(id);
        System.out.println("Deletado com sucesso");
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("fcc657a7-6fca-4587-af8b-76925c9d6891");

        var autor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        repository.delete(autor);

        System.out.println("Deletado com sucesso");
    }
}
