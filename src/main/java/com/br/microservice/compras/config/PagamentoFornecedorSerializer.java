package com.br.microservice.compras.config;

import com.br.microservice.compras.cliente.payload.request.RealizarPagamentoFinanceiro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.serializer.Serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class PagamentoFornecedorSerializer {
        //implements Serializer<RealizarPagamentoFinanceiro> {

//    private final ObjectMapper objectMapper;
//
//
//    private final ObjectMapper objectMapper;
//
//
//    @Override
//    public byte[] serialize(String s, RealizarPagamentoFinanceiro realizarPagamentoFinanceiro) {
//        try {
//            if (Objects.nonNull(realizarPagamentoFinanceiro)) {
//
//                String message = objectMapper.writeValueAsString(realizarPagamentoFinanceiro);
//                return message.getBytes(StandardCharsets.UTF_8);
//
//            }
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return new byte[0];
//    }
//
//    public PagamentoFornecedorSerializer() {
//        this.objectMapper = new ObjectMapper();
//    }
}
