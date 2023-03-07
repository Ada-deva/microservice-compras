package com.br.microservice.compras.exception;

public class InformacaoNaoEncontradaException extends Exception{

    public InformacaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
