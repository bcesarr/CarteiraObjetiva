package com.carteiraobjetiva.carteiraobjetiva.service;

import com.carteiraobjetiva.carteiraobjetiva.model.Conta;
import com.carteiraobjetiva.carteiraobjetiva.repository.ContaRepository;
import org.springframework.stereotype.Service;

import com.carteiraobjetiva.carteiraobjetiva.exception.ContaNaoEncontradaException;
import com.carteiraobjetiva.carteiraobjetiva.exception.SaldoInsuficienteException;
import com.carteiraobjetiva.carteiraobjetiva.exception.ValorInvalidoException;

import java.util.List;
// import java.util.ArrayList;
// import java.util.Map;
// import java.util.HashMap;

@Service
public class ContaService {

    // private Map<Long, Conta> contas = new HashMap<>();
    // private Long proximoId = 1L;

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
     }

    // private Conta obterContaOuErro(Long id) {
    //     Conta conta = contas.get(id);
    //     if (conta == null) {
    //         throw new ContaNaoEncontradaException(id);
    //     }
    //     return conta;
    // }

    private Conta obterContaOuErro(Long id) {
        return contaRepository.findById(id)
            .orElseThrow(() -> new ContaNaoEncontradaException(id));
    }

    // public Conta criarConta(String nomeTitular, double saldoInicial) {
    //     Conta conta = new Conta(proximoId, nomeTitular, saldoInicial);
    //     contas.put(proximoId, conta);
    //     proximoId++;
    //     return conta;
    // }

    public Conta criarConta(String nomeTitular, double saldoInicial) {
        Conta conta = new Conta(nomeTitular, saldoInicial);
        return contaRepository.save(conta);
    }

    // public void depositar(Long id, double valor) {
    //     if (valor <= 0) {
    //         throw new ValorInvalidoException();
    //     }
        
    //     Conta conta = obterContaOuErro(id);
    //     conta.depositar(valor);
    // }

    public void depositar(Long id, double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }

        Conta conta = obterContaOuErro(id);
        conta.depositar(valor);

        contaRepository.save(conta);
    }

    // public boolean sacar(Long id, double valor) {
    //     if (valor <= 0) {
    //         throw new ValorInvalidoException();
    //     }

    //     Conta conta = obterContaOuErro(id);

    //     boolean saqueRealizado = conta.sacar(valor);
    //     if (!saqueRealizado) {
    //         throw new SaldoInsuficienteException();
    //     }

    //     return true;
    // }

    public boolean sacar(Long id, double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }

        Conta conta = obterContaOuErro(id);

        boolean saqueRealizado = conta.sacar(valor);
        if (!saqueRealizado) {
            throw new SaldoInsuficienteException();
        }

        contaRepository.save(conta);
        
        return true;
    }

    // public boolean transferir(Long idOrigem, Long idDestino, double valor) {
    //     if (valor <= 0) {
    //         throw new ValorInvalidoException();
    //     }
        
    //     Conta contaOrigem = obterContaOuErro(idOrigem);
    //     Conta contaDestino = obterContaOuErro(idDestino);
        
    //     boolean saqueRealizado = contaOrigem.sacar(valor);

    //     if (!saqueRealizado) {
    //         throw new SaldoInsuficienteException();
    //     }

    //     contaDestino.depositar(valor);
        
    //     return true;
    // }

    public boolean transferir(Long idOrigem, Long idDestino, double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }

        Conta contaOrigem = obterContaOuErro(idOrigem);
        Conta contaDestino = obterContaOuErro(idDestino);

        boolean saqueRealizado = contaOrigem.sacar(valor);

        if (!saqueRealizado) {
            throw new SaldoInsuficienteException();
        }

        contaDestino.depositar(valor);

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
        
        return true;
    }

    public double verSaldo(Long id) {
        Conta conta = obterContaOuErro(id);
        return conta.getSaldo();
    }
    
    // public List<Conta> listarContas() {
    //     return new ArrayList<>(contas.values());
    // }

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    public Conta buscarConta(Long id) {
        return obterContaOuErro(id);
    }
}
