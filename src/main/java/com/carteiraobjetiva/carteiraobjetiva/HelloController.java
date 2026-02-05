package com.carteiraobjetiva.carteiraobjetiva;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {
    @GetMapping("/")
    public String hello() {
        return "API da Carteira Objetiva funcionando!";
    }
}