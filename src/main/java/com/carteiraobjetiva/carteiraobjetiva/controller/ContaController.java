package com.carteiraobjetiva.carteiraobjetiva.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.carteiraobjetiva.carteiraobjetiva.model.Conta;
import com.carteiraobjetiva.carteiraobjetiva.service.ContaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/criar")
    public Conta criarConta(@RequestParam String nomeTitular, @RequestParam double saldoInicial) {
        return contaService.criarConta(nomeTitular, saldoInicial);
    }

    @PostMapping("/depositar")
    public void depositar(@RequestParam Long id, @RequestParam double valor) {
        contaService.depositar(id, valor);
    }

    @PostMapping("/sacar")
    public boolean sacar(@RequestParam Long id, @RequestParam double valor) {
        return contaService.sacar(id, valor);
    }

    @GetMapping("/verSaldo")
    public double verSaldo(@RequestParam Long id) {
        return contaService.verSaldo(id);
    }

    @GetMapping("/listar")
    public Map<String, Object> listarContas() {
        List<Conta> lista = contaService.listarContas();

        Map<String, Object> resposta = new HashMap<>();

        if (lista.isEmpty()) {
            resposta.put("Mensagem", "Nenhuma conta foi cadastrada ainda.");
        } else {
            resposta.put("Mensagem", "Contas cadastradas:");
        }
        resposta.put("Contas", lista);
        return resposta;
    }

    // public List<Conta> listarContas() {
    //     return contaService.listarContas();
    // }

    
    @GetMapping("/{id}")
    public Conta buscarConta(@PathVariable Long id) {
        return contaService.buscarConta(id);
    }
}
