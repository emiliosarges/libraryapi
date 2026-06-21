package com.souemilio.libraryapi.controller;

import com.souemilio.libraryapi.repository.LivroRepository;
import com.souemilio.libraryapi.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;

}
