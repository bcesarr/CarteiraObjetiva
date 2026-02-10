package com.carteiraobjetiva.carteiraobjetiva.dto;

// (Agora) Uso do Lombok para gerar getters e setters automaticamente
import lombok.Data;

@Data
public class CriarContaRequestDTO {
    
    private String nomeTitular;
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