// Classe personalizada de exceção para valores inválidos
package com.carteiraobjetiva.carteiraobjetiva.exception;

// Herda de RuntimeException para representar erros de valor inválido
public class ValorInvalidoException extends RuntimeException {

    public ValorInvalidoException() {
        super("Valor deve ser maior que zero");
    }
}