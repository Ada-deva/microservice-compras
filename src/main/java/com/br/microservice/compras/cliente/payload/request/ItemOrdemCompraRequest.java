package com.br.microservice.compras.cliente.payload.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ItemOrdemCompraRequest {
    @NotNull
    private InsumoRequest insumo;
    @Min(value = 1, message = "Informe um valor valido para o preco do produto")
    private double preco;
    @Min(value = 1, message = "Informe um valor valido para quantidade do produto")
    private int quantidade;

}
