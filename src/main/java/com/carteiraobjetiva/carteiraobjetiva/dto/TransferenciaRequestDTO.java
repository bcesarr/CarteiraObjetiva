package com.carteiraobjetiva.carteiraobjetiva.dto;

// (Agora) Uso do Lombok para gerar getters e setters automaticamente
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;

@Data
public class TransferenciaRequestDTO {

    @NotNull(message = "ID da conta de origem é obrigatório")
    private Long idOrigem;

    @NotNull(message = "ID da conta de destino é obrigatório")
    private Long idDestino;
    
    @NotNull(message = "Valor da transferência é obrigatório")
    @Positive(message = "Valor da transferência deve ser positivo(maior que zero)")
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