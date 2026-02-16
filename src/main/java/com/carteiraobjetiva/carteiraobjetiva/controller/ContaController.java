package com.carteiraobjetiva.carteiraobjetiva.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import jakarta.validation.Valid;

import com.carteiraobjetiva.carteiraobjetiva.model.Conta;
import com.carteiraobjetiva.carteiraobjetiva.service.ContaService;
import com.carteiraobjetiva.carteiraobjetiva.service.CotacaoMoedaService;
import com.carteiraobjetiva.carteiraobjetiva.dto.CriarContaRequestDTO;
import com.carteiraobjetiva.carteiraobjetiva.dto.TransferenciaRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;
    private final CotacaoMoedaService cotacaoMoedaService;

    public ContaController(ContaService contaService, CotacaoMoedaService cotacaoMoedaService) {
        this.contaService = contaService;
        this.cotacaoMoedaService = cotacaoMoedaService;
    }

    // RequestParam = parâmetros da requisição
    // RequestParam → geralmente usado pra filtros, configurações, dados auxiliares
    
    // PathVariable = identidade do recurso
    // PathVariable → usado pra identificar um recurso específico

    // Utilizamos o RequestParam já que o valor vem da Query String, ou seja, não faz parte da rota
    @PostMapping("/criar")
    public Conta criarConta(@RequestBody @Valid CriarContaRequestDTO request) {
        return contaService.criarConta(
            request.getNomeTitular(),
            request.getSaldoInicial()
        );
    }

    @PostMapping("/{id}/depositar")
    public void depositar(@PathVariable Long id, @RequestParam double valor) {
        contaService.depositar(id, valor);
    }

    @PostMapping("/{id}/sacar")
    public boolean sacar(@PathVariable Long id, @RequestParam double valor) {
        return contaService.sacar(id, valor);
    }

    @PostMapping("/transferir")
    public boolean transferir(@RequestBody @Valid TransferenciaRequestDTO request) {
        return contaService.transferir(
            request.getIdOrigem(),
            request.getIdDestino(),
            request.getValor()
        );
    }

    @GetMapping("/{id}/verSaldo")
    public Map<String, Object> verSaldo(@PathVariable Long id) {
        double saldo = contaService.verSaldo(id);
        Map<String, Object> resposta = new LinkedHashMap<>(); // Mantém a ordem de inserção se usar o Linked mas e mais lento e utiliza mais memória
                                                              // HashMap é mais rápido e utiliza menos memória, mas a ordem de inserção é imprevisivel

        resposta.put("Id", id);
        resposta.put("Saldo da conta:", saldo);
        return resposta;
    }


    // public double verSaldo(@RequestParam Long id) {
    //     return contaService.verSaldo(id);
    // }

    @GetMapping("/listar")
    public Map<String, Object> listarContas() {
        List<Conta> lista = contaService.listarContas();

        Map<String, Object> resposta = new LinkedHashMap<>();

        if (lista.isEmpty()) {
            resposta.put("Mensagem", "Nenhuma conta foi cadastrada ainda.");
        } else {
            resposta.put("Mensagem", "Contas cadastradas:");
        }
        
        resposta.put("Contas", lista);
        return resposta;
    }

    // Modelo simples para exibir lista vazia quando não há contas cadastradas
    // public List<Conta> listarContas() {
    //     return contaService.listarContas();
    // }

    // Utilizamos aqui o PathVariable para buscar a conta pelo Id, no caminho da URL, ja que o valor faz parte da rota
    @GetMapping("/{id}")
    public Conta buscarConta(@PathVariable Long id) {
        return contaService.buscarConta(id);
    }

    // Endpoint para converter o saldo da conta para outra moeda
    @GetMapping("/{id}/converter")
    public Map<String, Object> converterSaldo(
        @PathVariable Long id,
        @RequestParam String moeda
    ) {
        // Busca a conta pelo ID
        Conta conta = contaService.buscarConta(id);
        
        // Pega o saldo atual da conta em Reais (BRL)
        double saldoBRL = conta.getSaldo();

        // Converte o saldo para a moeda solicitada usando a API de cotação externa 
        double valorConvertido = cotacaoMoedaService.converterValor(saldoBRL, moeda);

        // Obtem a taxa de câmbio atual
        double taxaCambio = cotacaoMoedaService.obterTaxaCambio(moeda);

        // Monta a resposta em formato de mapa (JSON)
        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("Id", conta.getId());
        resposta.put("nomeTitular", conta.getNomeTitular());
        resposta.put("saldoBRL", saldoBRL);
        resposta.put("moedaDestino", moeda.toUpperCase());
        resposta.put("valorConvertido", valorConvertido);
        resposta.put("taxaCambio", taxaCambio);
        resposta.put("dataConsulta", java.time.LocalDateTime.now());

        return resposta;
    }
}
