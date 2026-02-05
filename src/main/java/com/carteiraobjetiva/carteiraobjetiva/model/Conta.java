package com.carteiraobjetiva.carteiraobjetiva.model;

public class Conta {

    private Long id;
    private String nomeTitular;
    private double saldo;

    public Conta(Long id, String nomeTitular, double saldoInicial) {
        this.id = id;
        this.nomeTitular = nomeTitular;
        this.saldo = saldoInicial;
    }

    public Long getId() {
        return id;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && saldo >= valor) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }
}