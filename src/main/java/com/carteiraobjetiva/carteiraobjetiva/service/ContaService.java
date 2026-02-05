package com.carteiraobjetiva.carteiraobjetiva.service;

import com.carteiraobjetiva.carteiraobjetiva.model.Conta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContaService {

    private Map<Long, Conta> contas = new HashMap<>();
    private Long proximoId = 1L;

    public Conta criarConta(String nomeTitular, double saldoInicial) {
        Conta conta = new Conta(proximoId, nomeTitular, 0.0);
        contas.put(proximoId, conta);
        proximoId++;
        return conta;
    }

    public List<Conta> listarContas() {
        return new ArrayList<>(contas.values());
    }

    public Conta buscarConta(Long id) {
        return contas.get(id);
    }

    public void depositar(Long id, double valor) {
        Conta conta = contas.get(id);
        if (conta != null) {
            conta.depositar(valor);
        }
    }

    public boolean sacar(Long id, double valor) {
        Conta conta = contas.get(id);
        if (conta != null) {
            return conta.sacar(valor);
        }
        return false;
    }
}
