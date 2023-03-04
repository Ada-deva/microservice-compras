package com.br.microservice.compras.cliente.payload.request;

import lombok.Data;

@Data
public class FornecedorFinanceiroRequest {
    private String cnpj;
    private String nomeFornecedor;
    private String identificador;
}
