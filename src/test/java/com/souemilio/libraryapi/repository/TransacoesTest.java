package com.souemilio.libraryapi.repository;

import com.souemilio.libraryapi.service.TransacaoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacoesService;

    /**
     * Commit
     * Rollback
     */

    @Test
    void transacaoSimples(){
        transacoesService.executar();
    }

    @Test
    void transacaoEstadoManaged(){
        transacoesService.atualiacaoSemAtualizar();
    }





}

