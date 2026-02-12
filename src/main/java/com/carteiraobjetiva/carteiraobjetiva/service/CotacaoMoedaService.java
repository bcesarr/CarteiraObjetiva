package com.carteiraobjetiva.carteiraobjetiva.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

// O Spring vai criar uma instância automaticamente (Injeção de Dependência)
@Service
public class CotacaoMoedaService {

    // WebClient é o cliente HTTP moderno do Spring para fazer requisições
    private final WebClient webClient;

    // Usamos BRL (Real) como moeda base
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/BRL";

    // Construtor para injetar o WebClient automaticamente
    public CotacaoMoedaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }

    /**
    * @param moedaDestino Sigla da moeda de destino
    * @return Taxa de câmbio entre a moeda de origem (BRL) e a moeda de destino
    */

    public double obterTaxaCambio(String moedaDestino) {

        try {
            // Faz a chamada à API externa para obter as taxas de câmbio
            Map<String, Object> response = webClient
                    .get()
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            // Extrai a taxa de câmbio do JSON retornado
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            return rates.getOrDefault(moedaDestino.toUpperCase(), 0.0);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter taxa de câmbio: " + e.getMessage());
        }
    }

    /**
    * @Param valorBRL
    * @Param moedaDestino
    * @return valor convertido para a moeda de destino
    */
    public double converterValor(double valorBRL, String moedaDestino) {
        double taxaCambio = obterTaxaCambio(moedaDestino);

        if (taxaCambio == 0.0) {
            throw new RuntimeException("Moeda de destino inválida: " + moedaDestino + " ou taxa de câmbio não disponível.");
        }
        return valorBRL * taxaCambio;
    }
}