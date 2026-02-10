// Classe personalizada de exceção para quando uma conta não é encontrada
package com.carteiraobjetiva.carteiraobjetiva.exception;

// Herda de RuntimeException para representar erros de conta não encontrada
public class ContaNaoEncontradaException extends RuntimeException {

    public ContaNaoEncontradaException(Long id) {
        super("Conta não encontrada com id: " + id);
    }
}