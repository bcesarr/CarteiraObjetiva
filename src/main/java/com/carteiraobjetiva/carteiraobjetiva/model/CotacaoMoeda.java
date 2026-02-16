package com.carteiraobjetiva.carteiraobjetiva.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade para armazenar cotações de moedas em cache no banco de dados
 * Isso evita chamar a API externa a cada requisição, economizando requests
 */
@Entity
@Table(name = "cotacoes_moeda")
public class CotacaoMoeda {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) é necessário para que o JPA gere o ID automaticamente
    // O que o GeneratedValue faz é indicar que o valor do ID deve ser gerado automaticamente pelo banco de dados, usando a estratégia de identidade (Identity), onde o banco de dados é responsável por gerar um valor único para cada nova entrada.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @Column(nullable = false, unique = true) é necessário para garantir que o campo moeda seja único e não nulo no banco de dados. Isso é importante para evitar duplicatas e garantir a integridade dos dados.
    // O que o Column faz é configurar as propriedades da coluna no banco de dados, como se ela pode ser nula (nullable) e se deve ser única (unique). No caso, estamos dizendo que a coluna moeda não pode ser nula e deve ser única, ou seja, não pode haver duas cotações para a mesma moeda.
    @Column(nullable = false, unique = true)
    private String codigoMoeda;

    // Taxa de conversão em relação ao Real (BRL)
    @Column(nullable = false)
    private double taxaConversao;

    // Data e hora da última atualização da cotação
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    // Construtor padrão (vazio) necessário para o JPA
    public CotacaoMoeda() {
    }

    // Construtor completo para facilitar a criação de objetos CotacaoMoeda e de novos registros no banco de dados
    public CotacaoMoeda(String codigoMoeda, double taxaConversao) {
        this.codigoMoeda = codigoMoeda;
        this.taxaConversao = taxaConversao;
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Getters e Setters (manuais sem usar lombok (para caso de estudo)
    public Long getId() {
        return id;
    }

    public String getCodigoMoeda() {
        return codigoMoeda;
    }

    public void setCodigoMoeda(String codigoMoeda) {
        this.codigoMoeda = codigoMoeda;
    }

    public double getTaxaConversao() {
        return taxaConversao;
    }

    public void setTaxaConversao(double taxaConversao) {
        this.taxaConversao = taxaConversao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

     /**
     * Verifica se a cotação está desatualizada (mais de 1 hora)
     * @return true se precisa atualizar, false se ainda está válida
     */

     public boolean precisaAtualizar() {
        // LocalDateTime.now() pega a data/hora atual
        // minusHours(1) subtrai 1 hora
        // isAfter() verifica se a data de atualização é DEPOIS de 1 hora atrás
        // Se for ANTES de 1 hora atrás, retorna true (precisa atualizar)
        return this.dataAtualizacao.isBefore(LocalDateTime.now().minusHours(1));
     }
}