package com.carteiraobjetiva.carteiraobjetiva.model;

import javax.annotation.processing.Generated;

import jakarta.persistence.*;

@Entity
@Table(name = "contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeTitular;

    private double saldo;

    // Construtor padrão(vazio) necessário para o JPA
    public Conta() {
    }

    public Conta(String nomeTitular, double saldoInicial) {
        // this.id = id;
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