package com.br.microservice.compras.producer;


import com.br.microservice.compras.model.OrdemCompra;
import com.br.microservice.compras.queue.out.OrdemCompraMessageSender;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class OrdemCompraProducer {

    @Autowired
    private OrdemCompraMessageSender ordemCompraMessageSender;
    public void enviarOrdemCompraParaFinanceiro(OrdemCompra ordemCompra) {;
        ordemCompraMessageSender.send(ordemCompra);
    }

    public void enviarOrdemCompraParaFinanceiroPagar(String ordemCompra) {;
        ordemCompraMessageSender.sendOrdemCompraParaPagamento(ordemCompra);
    }
}
