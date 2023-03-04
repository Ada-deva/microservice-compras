package com.br.microservice.compras.config;

import lombok.Value;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPagarFornecedorSenderConfig {
//    @Value("${business.financeiro.message.queue.pagamento_fornecedor}")
//    private String queueName;
//
//    @Bean
//    public Queue queue() {
//        return new Queue(queueName, true);
//    }
}


