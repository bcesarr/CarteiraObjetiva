package com.carteiraobjetiva.carteiraobjetiva.dto;

// (Agora) Uso do Lombok para gerar getters e setters automaticamente
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;

@Data
public class CriarContaRequestDTO {
    
    @NotBlank(message = "Nome do titular é obrigatório")
    private String nomeTitular;


    @NotNull(message = "Saldo inicial é obrigatório")
    @Positive(message = "Saldo inicial deve ser positivo(maior que zero)")
    private double saldoInicial;
}

// -------------------------------- \\

// (Antes) Sem o uso do Lombok
// public class CriarContaRequestDTO {
    
//     private String nomeTitular;
//     private double saldoInicial;

//     public String getNomeTitular() {
//         return nomeTitular;
//     }

//     public void setNomeTitular(String nomeTitular) {
//         this.nomeTitular = nomeTitular;
//     }

//     public double getSaldoInicial() {
//         return saldoInicial;
//     }

//     public void setSaldoInicial(double saldoInicial) {
//         this.saldoInicial = saldoInicial;
//     }
// }