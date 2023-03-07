package com.br.microservice.compras.queue.out;

import com.br.microservice.compras.model.Fornecedor;
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
public class FornecedorMessageSender {
    private final RabbitTemplate rabbitTemplate;

    private final Queue queue;

    private final ObjectMapper objectMapper;

    // Envia o fornecedor para salvar
    public  void send(Fornecedor fornecedor) {
        String message = null;
        try {
            message = objectMapper.writeValueAsString(fornecedor);
            log.info("Mensagem enviada para o Rabbit {}", fornecedor);
            rabbitTemplate.convertSendAndReceive(queue.getName(),message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}