package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "API do Gerenciador de Agendamentos de Barbearia rodando!";
    }
}
