package com.br.microservice.compras.service;

import com.br.microservice.compras.model.ItemOrdemCompra;
import com.br.microservice.compras.model.ItemOrdemCompraComposedKey;
import com.br.microservice.compras.model.OrdemCompra;
import com.br.microservice.compras.repository.ItemOrdemCompraRepository;
import com.br.microservice.compras.repository.OrdemCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatedOrdemCompraService {
    private final OrdemCompraRepository ordemCompraRepository;
    private final ItemOrdemCompraRepository itemOrdemCompraRepository;

    public OrdemCompra execute(OrdemCompra ordemCompra, List<ItemOrdemCompra> itemOrdemCompras) {
        OrdemCompra salvarOrdem = ordemCompraRepository.save(ordemCompra);
        itemOrdemCompras
                .forEach(itemOrdem ->
                        itemOrdem.setItemOrdemCompraComposedKey
                                (new ItemOrdemCompraComposedKey(salvarOrdem.getId(), itemOrdem.getInsumo().getId())));
        itemOrdemCompraRepository.saveAll(itemOrdemCompras);
        salvarOrdem.setListaInsumos(itemOrdemCompras);
        return salvarOrdem;
    }
}