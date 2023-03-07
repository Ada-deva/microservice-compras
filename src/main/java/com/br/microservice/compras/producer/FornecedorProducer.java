package com.br.microservice.compras.producer;

import com.br.microservice.compras.model.Fornecedor;
import com.br.microservice.compras.queue.out.FornecedorMessageSender;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class FornecedorProducer {
    @Autowired
    private FornecedorMessageSender fornecedorMessageSender;
    public void enviarFornecedorParaFinanceiro(Fornecedor fornecedor) {;
        fornecedorMessageSender.send(fornecedor);
    }
}
