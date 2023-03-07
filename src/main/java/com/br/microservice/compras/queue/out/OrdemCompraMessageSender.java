package com.br.microservice.compras.queue.out;


import com.br.microservice.compras.model.OrdemCompra;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrdemCompraMessageSender {
    private final RabbitTemplate rabbitTemplate;

    private final Queue ordemCompraQueue;

    private final Queue ordemCompraPagarQueue;

    private final ObjectMapper objectMapper;

    public void send(OrdemCompra ordemCompra) {
        String message = null;
        try {
            message = objectMapper.writeValueAsString(ordemCompra);
            log.info("Mensagem enviada para o Rabbit {}", ordemCompra);
            rabbitTemplate.convertSendAndReceive(ordemCompraQueue.getName(),message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendOrdemCompraParaPagamento(String ordemCompra) {
        String message = null;
        try {
            message = objectMapper.writeValueAsString(ordemCompra);
            log.info("Mensagem enviada para o Rabbit {}", ordemCompra);
            rabbitTemplate.convertSendAndReceive(ordemCompraPagarQueue.getName(),message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
