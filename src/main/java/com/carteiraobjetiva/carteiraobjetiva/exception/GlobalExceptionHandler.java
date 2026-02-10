// Classe global de exceções personalizadas
package com.carteiraobjetiva.carteiraobjetiva.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // fallback para RuntimeException, que é a superclasse de muitas exceções comuns
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> tratarRuntime(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro interno inesperado"));
    }

    // exceção personalizada para conta não encontrada
    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> tratarContaNaoEncontrada(ContaNaoEncontradaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    // exceção personalizada para valor inválido (ex: valor negativo)
    @ExceptionHandler(ValorInvalidoException.class)
    public ResponseEntity<Map<String, String>> tratarValorInvalido(ValorInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    // exceção personalizada para saldo insuficiente
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<Map<String, String>> tratarSaldoInsuficiente(SaldoInsuficienteException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }
}

