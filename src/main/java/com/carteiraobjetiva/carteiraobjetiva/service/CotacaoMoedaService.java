package com.carteiraobjetiva.carteiraobjetiva.service;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

// O Spring vai criar uma instância automaticamente (Injeção de Dependência)
@Service
public class CotacaoMoedaService {

    // RestTemplate é uma classe do Spring que facilita a comunicação com APIs RESTful, permitindo fazer requisições HTTP de forma simples e eficiente
    private final RestTemplate restTemplate;

    // Usamos BRL (Real) como moeda base
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/BRL";

    // Construtor para inicializar o RestTemplate, que é usado para fazer chamadas HTTP
    public CotacaoMoedaService() {
        this.restTemplate = new RestTemplate();
    }

    /**
    * @param moedaDestino Sigla da moeda de destino
    * @return Taxa de câmbio entre a moeda de origem (BRL) e a moeda de destino
    * @throws RuntimeException se ocorrer um erro ao obter a taxa de câmbio ou se a moeda de destino for inválida
    */

    public double obterTaxaCambio(String moedaDestino) {

        try {
            // Faz a chamada à API externa para obter as taxas de câmbio
            // Faz a requisição HTTP GET e recebe o JSON como Map
            // ParameterizedTypeReference garante type safety
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                    API_URL,                                                    // URL da API
                    HttpMethod.GET,                                             // Método HTTP (GET)
                    null,                                                       // Request body (null porque é GET)
                    new ParameterizedTypeReference<Map<String, Object>>() {}    // Tipo de resposta esperada (Map<String, Object> para o JSON retornado)
                );
            
            // Pega o corpo da resposta HTTP (o JSON convertido em Map)
            Map<String, Object> response = responseEntity.getBody();

            // Verifica se a resposta da API é nula
            if (response == null) {
                throw new RuntimeException("API retornou uma resposta vazia");
            }

            // Extrai o objeto "rates" que contém as cotações, do JSON retornado
            Object ratesObject = response.get("rates");

            // Verifica se o campo "rates" existe e é do tipo esperado (Map) antes de fazer o cast
            // cast é o processo de converter um tipo de dado para outro, nesse caso, estamos convertendo o objeto genérico para um Map específico
            if (ratesObject == null || !(ratesObject instanceof Map)) {
                throw new RuntimeException("Resposta da API não contém o campo 'rates' ou está em formato invalido");
            }

            // Realiza o cast do objeto "rates" para um Map<String, Object> para acessar as taxas de câmbio
            @SuppressWarnings("unchecked")
            Map<String, Object> rates = (Map<String, Object>) ratesObject;

            if (rates.isEmpty()) {
                throw new RuntimeException("Erro: Campo 'rates' está vazio. Nenhuma cotação disponível.");
            }
            Object taxaObject = rates.get(moedaDestino.toUpperCase());

            if (taxaObject == null) {
                return 0.0;
            }

            double taxa;
            
            if (taxaObject instanceof Double) {
                taxa = (Double) taxaObject;
            } else if (taxaObject instanceof Integer) {
                taxa = ((Integer) taxaObject).doubleValue();            
            } else if (taxaObject instanceof Long) {
                taxa = ((Long) taxaObject).doubleValue();
            } else if (taxaObject instanceof Float) {
                taxa = ((Float) taxaObject).doubleValue();
            } else if (taxaObject instanceof Number) {
                taxa = ((Number) taxaObject).doubleValue();
            } else if (taxaObject instanceof String) {
                try {
                    taxa = Double.parseDouble((String) taxaObject);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Erro: A taxa de câmbio para a moeda " + moedaDestino + " não é um número válido. Valor recebido: " + taxaObject, e);
                }
            } else {
                throw new RuntimeException("Erro: Tipo de dado inesperado para a taxa de câmbio da moeda " + moedaDestino + ". Valor recebido: " + taxaObject);
            }

            if (Double.isNaN(taxa) || Double.isInfinite(taxa)) {
                throw new RuntimeException("Taxa inválida (NaN ou Infinito) para moeda: " + moedaDestino);
            }

            return taxa;
            
            
        } catch (RestClientException e) {
            // Captura erros de rede/conexão
            // Exemplos: Timeout, DNS falhou, API fora do ar, sem internet
            throw new RuntimeException("Erro de conexão ao buscar cotação: " + e.getMessage() + 
                                       ". Verifique sua conexão com a internet ou tente novamente mais tarde.", e);
        
        } catch (RuntimeException e) {
            throw e;

        } catch (Exception e) {
            // Extrai a taxa de câmbio do JSON retornado
            // Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            // return rates.getOrDefault(moedaDestino.toUpperCase(), 0.0);
            throw new RuntimeException("Erro ao obter taxa de câmbio: " + e.getMessage());
        }
    }

    /**
    * @Param valorBRL
    * @Param moedaDestino
    * @return valor convertido para a moeda de destino
    * @throws IllegalArgumentException se o valor for negativo
    * @throws RuntimeException se a moeda for inválida ou API falhar
    */
    public double converterValor(double valorBRL, String moedaDestino) {

        if (valorBRL < 0) {
            throw new IllegalArgumentException("Erro: Valor não pode ser negativo. " +
                                               "Recebido: " + valorBRL);
        }
        
        if (moedaDestino == null || moedaDestino.trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: Moeda de destino não pode ser nula ou vazia.");
        }

        double taxaCambio = obterTaxaCambio(moedaDestino);

        if (taxaCambio == 0.0) {
            throw new RuntimeException("Moeda de destino inválida: " + moedaDestino + " ou taxa de câmbio não disponível.");
        }

        if (taxaCambio > 1000000.0) {
            throw new RuntimeException("Erro: Taxa de câmbio anormalmente alta para a moeda " + moedaDestino + ": " + taxaCambio);
        }

        double valorConvertido = valorBRL * taxaCambio;

        if (!Double.isFinite(valorConvertido)) {
            throw new RuntimeException("Erro: Valor convertido é inválido (NaN ou infinito). " +
            "Valor original: " + valorBRL + ", Taxa de Cambio: " + taxaCambio);
        }

        return valorConvertido;
    }
}