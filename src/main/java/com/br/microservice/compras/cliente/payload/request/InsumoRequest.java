package com.br.microservice.compras.cliente.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class InsumoRequest {
    @NotEmpty(message = "O identificador do produto é requirido.")
    private String identificador;
    private String nome;
}
