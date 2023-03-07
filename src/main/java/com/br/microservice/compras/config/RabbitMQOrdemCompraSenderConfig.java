package com.br.microservice.compras.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQOrdemCompraSenderConfig {

    @Value("${business.compras.message.queue.ordem_compra}")
    private String queueName;

    @Value("${business.compras.message.queue.pagamento}")
    private String queuePagamento;

    @Bean
    public Queue ordemCompraQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Queue ordemCompraPagarQueue() {
        return new Queue(queuePagamento, true);
    }

}
