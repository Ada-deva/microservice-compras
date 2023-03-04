package com.br.microservice.compras.cliente.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RealizarPagamentoFinanceiro {
    @NotNull
    private FornecedorFinanceiroRequest fornecedor;
    @NotNull
    private List<ItemOrdemCompraRequest> itens;
}
