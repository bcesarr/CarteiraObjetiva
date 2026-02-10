// Classe personalizada de exceção para saldo insuficiente
package com.carteiraobjetiva.carteiraobjetiva.exception;

// Herda de RuntimeException para representar erros de saldo insuficiente
public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar a operação");
    }
}
