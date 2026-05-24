package com.souemilio.libraryapi.repository;

import com.souemilio.libraryapi.model.Autor;
import com.souemilio.libraryapi.model.GeneroLivro;
import com.souemilio.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

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

    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Mexicano");
        autor.setDataNascimento(LocalDate.of(1970, 8, 5));

        Livro livro = new Livro();
        livro.setIsbn("20251-20262");
        livro.setPreco(BigDecimal.valueOf(600));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("As belas tranças de um careca");
        livro.setDataPublicacao(LocalDate.of(1999,1,23));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("12345-54321");
        livro2.setPreco(BigDecimal.valueOf(204));
        livro2.setGenero(GeneroLivro.BIOGRAFIA);
        livro2.setTitulo("Os quatros cantos da mesa redonda");
        livro2.setDataPublicacao(LocalDate.of(2001,10,10));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        //Ao usar cascade, não é necessária a linha abaixo.
        // livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarLivrosAutorTest(){
        var id = UUID.fromString("892c2ff2-afb0-4dd6-ad1b-dd3ef7ac1ade");
        var autor = repository.findById(id).get();

        //bucar os livros do autor

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);
        autor.getLivros().forEach(System.out::println);
    }
}
