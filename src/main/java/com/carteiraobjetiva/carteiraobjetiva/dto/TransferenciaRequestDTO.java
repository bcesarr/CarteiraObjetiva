package com.carteiraobjetiva.carteiraobjetiva.dto;

// (Agora) Uso do Lombok para gerar getters e setters automaticamente
import lombok.Data;

@Data
public class TransferenciaRequestDTO {
    private Long idOrigem;
    private Long idDestino;
    private double valor;
}

// -------------------------------- \\

// (Antes) Sem o uso do Lombok
// public class TransferenciaRequestDTO {
//     private Long idOrigem;
//     private Long idDestino;
//     private double valor;

//     public Long getIdOrigem() {
//         return idOrigem;
//     }

//     public void setIdOrigem(Long idOrigem) {
//         this.idOrigem = idOrigem;
//     }

//     public Long getIdDestino() {
//         return idDestino;
//     }

//     public void setIdDestino(Long idDestino) {
//         this.idDestino = idDestino;
//     }

//     public double getValor() {
//         return valor;
//     }

//     public void setValor(double valor) {
//         this.valor = valor;
//     }
// }   