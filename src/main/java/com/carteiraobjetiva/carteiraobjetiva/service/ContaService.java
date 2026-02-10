package com.carteiraobjetiva.carteiraobjetiva.service;

import com.carteiraobjetiva.carteiraobjetiva.model.Conta;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Service
public class ContaService {

    private Map<Long, Conta> contas = new HashMap<>();
    private Long proximoId = 1L;

    private Conta obterContaOuErro(Long id) {
        Conta conta = contas.get(id);
        if (conta == null) {
            throw new RuntimeException("Conta não encontrada");
        }
        return conta;
    }

    public Conta criarConta(String nomeTitular, double saldoInicial) {
        Conta conta = new Conta(proximoId, nomeTitular, saldoInicial);
        contas.put(proximoId, conta);
        proximoId++;
        return conta;
    }

    public void depositar(Long id, double valor) {
        if (valor <= 0) {
            throw new RuntimeException("Valor deve ser maior que zero");
        }
        
        Conta conta = obterContaOuErro(id);
        conta.depositar(valor);
    }

    public boolean sacar(Long id, double valor) {
        if (valor <= 0) {
            throw new RuntimeException("Valor deve ser maior que zero");
        }

        Conta conta = obterContaOuErro(id);
        return conta.sacar(valor);
    }

    public boolean transferir(Long idOrigem, Long idDestino, double valor) {
        Conta contaOrigem = obterContaOuErro(idOrigem);
        Conta contaDestino = obterContaOuErro(idDestino);

        if (valor <= 0) {
            throw new RuntimeException("Valor deve ser maior que zero");
        }

        boolean saqueRealizado = contaOrigem.sacar(valor);

        if (!saqueRealizado) {
            throw new RuntimeException("Saldo insuficiente para transferência");
        }

        contaDestino.depositar(valor);
        return true;
    }

    public double verSaldo(Long id) {
        Conta conta = obterContaOuErro(id);
        return conta.getSaldo();
    }
    
    public List<Conta> listarContas() {
        return new ArrayList<>(contas.values());
    }

    public Conta buscarConta(Long id) {
        return obterContaOuErro(id);
    }
}
