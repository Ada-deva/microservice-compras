package com.br.microservice.compras.service;

import com.br.microservice.compras.model.Fornecedor;
import com.br.microservice.compras.model.Insumo;
import com.br.microservice.compras.model.ItemOrdemCompra;
import com.br.microservice.compras.model.OrdemCompra;
import com.br.microservice.compras.payloads.OrdemCompraRequest;
import com.br.microservice.compras.repository.OrdemCompraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdemCompraService {
    private final OrdemCompraRepository ordemCompraRepository;
    private final FornecedorService fornecedorService;
    private final CreateOrdemCompraService createOrdemCompraService;
    private final InsumoService insumoService;

    public void salvar(OrdemCompraRequest ordemCompraRequest) {
        Fornecedor fornecedor = salvarFornecedor(ordemCompraRequest);
        Insumo insumo = salvarInsumo(ordemCompraRequest);
        OrdemCompra ordemCompra = salvarOrdemCompra(ordemCompraRequest, insumo, fornecedor);

        //mandar para o financeiro -> sendPedidoToFinanceiro(fornecedor, insumo, ordemCompra);

    }

    private Fornecedor salvarFornecedor(OrdemCompraRequest ordemCompra) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(ordemCompra.getFornecedor().getNome());
        fornecedor.setCnpj(ordemCompra.getFornecedor().getCnpj());
        return fornecedorService.execute(fornecedor);
    }


    private OrdemCompra salvarOrdemCompra(OrdemCompraRequest ordemCompraRequest, Insumo insumo, Fornecedor fornecedor) {
        OrdemCompra ordemCompra = new OrdemCompra();
        ordemCompra.setFornecedor(fornecedor);
        ItemOrdemCompra itemOrdemCompra = new ItemOrdemCompra();
        itemOrdemCompra.setQuantidade(ordemCompraRequest.getQuantidade());
        itemOrdemCompra.setPreco(ordemCompraRequest.getPrecoCompra());
        itemOrdemCompra.setInsumo(insumo);
        return createOrdemCompraService.execute(ordemCompra, List.of(itemOrdemCompra));
    }


    private Insumo salvarInsumo(OrdemCompraRequest ordemCompraRequest) {
        Insumo insumo = new Insumo();
        insumo.setQuantidade(ordemCompraRequest.getUnidadeQuantidade());
        insumo.setMarca(ordemCompraRequest.getMarca());
        insumo.setNome(ordemCompraRequest.getNome());
        insumo.setUnidade(ordemCompraRequest.getUnidade());
        return insumoService.salvar(insumo);
    }

}


