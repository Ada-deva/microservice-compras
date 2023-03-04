package com.br.microservice.compras.payloads;

import lombok.Data;

@Data
public class FornecedorRequest {
    private String identificados;
    private String cnpj;
    private String nome;

}
