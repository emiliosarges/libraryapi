package com.souemilio.libraryapi.service;

import com.souemilio.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

   //construtor implementado pela anotação: @RequiredArgsConstructor

}
