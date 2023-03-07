package com.br.microservice.compras.queue.in;

import com.br.microservice.compras.exception.InformacaoNaoEncontradaException;
import com.br.microservice.compras.service.OrdemCompraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdemCompraMessageConsumer {

    private final ObjectMapper objectMapper;
    private final OrdemCompraService ordemCompraService;
    @RabbitListener(queues = {"${business.compras.message.queue.ordem_compra_pago}"})

    public void receiveMessage(String message) throws JsonProcessingException, InformacaoNaoEncontradaException {
        String identificador = objectMapper.readValue(message, String.class);
        ordemCompraService.atualizarOrdemPaga(identificador);
    }
}
