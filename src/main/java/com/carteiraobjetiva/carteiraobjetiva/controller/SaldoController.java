package com.carteiraobjetiva.carteiraobjetiva.controller;

import com.carteiraobjetiva.carteiraobjetiva.service.SaldoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaldoController {
    
    private final SaldoService saldoService;

    public SaldoController(SaldoService saldoService) {
        this.saldoService = saldoService;
    }

    @GetMapping("/saldo")
    public double obterSaldo() {
        return saldoService.obterSaldo();
    }
}