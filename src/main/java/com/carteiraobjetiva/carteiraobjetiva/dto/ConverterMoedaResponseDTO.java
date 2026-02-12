package com.carteiraobjetiva.carteiraobjetiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

// Anotação para gerar construtor com todos os argumentos
@AllArgsConstructor

// Anotação para gerar construtor sem argumentos - necessário para Spring converter JSON automaticamente
@NoArgsConstructor
public class ConverterMoedaResponseDTO {

    private Long id;
    private String NomeTitular;
    private double saldo;
    private String moedaDestino;
    private double valorConvertido;
    private double taxaCambio;
    private LocalDateTime dataConsulta;
}