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

    // private Conta obterContaOuErro(Long id) {
    //     Conta conta = contas.get(id);
    //     if (conta == null) {
    //         throw new RuntimeException("Conta não encontrada");
    //     }
    //     return conta;
    // }

    public Conta criarConta(String nomeTitular, double saldoInicial) {
        Conta conta = new Conta(proximoId, nomeTitular, saldoInicial);
        contas.put(proximoId, conta);
        proximoId++;
        return conta;
    }

    public void depositar(Long id, double valor) {
        Conta conta = contas.get(id);
        if (conta == null) {
            throw new RuntimeException("Conta não encontrada");
        }
        conta.depositar(valor);
    }

    public boolean sacar(Long id, double valor) {
        Conta conta = contas.get(id);
        if (conta == null) {
            throw new RuntimeException("Conta não encontrada");
        }
        return conta.sacar(valor);
    }

    public double verSaldo(Long id) {
        Conta conta = contas.get(id);
        if (conta == null) {
            throw new RuntimeException("Conta não encontrada");
        }
        return conta.getSaldo();
    }
    
    public List<Conta> listarContas() {
        return new ArrayList<>(contas.values());
    }

    public Conta buscarConta(Long id) {
        Conta conta = contas.get(id);
        if (conta == null) {
            throw new RuntimeException("Conta não encontrada");
        }
        return conta;
    }
}
